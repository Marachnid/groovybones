package groovybones
/**
 * Represents a User DB entity in MySQL table, 'user'
 * Implicitly maps User class to 'user' table
 * Flat one-to-Many relationship with SavedGame
 */
class User {
    String cognitoSub
    String username
    int wins
    int losses
    int totalScore


    //validates fields beforehand
    static constraints = {
        cognitoSub nullable: false, blank: false, unique: true, updatable: false
        username nullable: false, blank: false, maxSize: 25         //assuming that cognito handles duplicate usernames
        wins min: 0
        losses min: 0
        totalScore min: 0
    }

    //DB datatype mappings
    static mapping = {
        cognitoSub updatable: false
        version false
    }
}