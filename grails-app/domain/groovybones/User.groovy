package groovybones
/**
 * Represents a User DB entity in MySQL table, 'user'
 * Implicitly maps User class to 'user' table
 * Persistence layer objects (User.get(id)+others) possess implicit variables that can be accessed
 * One-to-Many relationship with SavedGame
 */
class User {
    String cognitoSub       //taking another look at if necessary to even have here if there's time
    String username
    int wins
    int losses
    int totalScore

    //foreign key - one to many relationship
    static hasMany = [savedGames: SavedGame]

    //validates fields beforehand - cognitoSub is removed from in-memory retrievals and can't be validated
    static constraints = {
        username nullable: false, blank: false, maxSize: 25         //assuming that cognito handles duplicate usernames
        wins nullable: false, min: 0
        losses nullable: false, min: 0
        totalScore nullable: false, min: 0
    }

    //DB datatype mappings
    static mapping = {
        cognitoSub updatable: false //doesn't work as intended, working around it manually in service
        version false
    }

    /**
     * utility method for returning opponent as a map of values
     * if detached copy - ID is inserted on new UserService.getUserById(id)
     * ID is not pulled out persistence objects on new User(existing.returnAsMap()), has to be added post
     * @return opponent as map with ID and absent cognitoSub
     * @See UserService
     */
    Map returnAsMap() {
        [id: id, username: username, wins: wins, losses: losses, totalScore: totalScore, savedGames: savedGames]
    }
}