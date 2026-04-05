package groovybones
/**
 * Represents a User DB entity in MySQL table, 'user'
 * Implicitly maps User class to 'user' table
 * One-to-Many relationship with SavedGame
 */
class User {
    String cognitoSub
    String username
    int wins
    int losses
    int totalScore


    //validates fields beforehand - cognitoSub is removed from in-memory retrievals and can't be validated
    static constraints = {
        username nullable: false, blank: false, maxSize: 25         //assuming that cognito handles duplicate usernames
        wins nullable: false, min: 0
        losses nullable: false, min: 0
        totalScore nullable: false, min: 0
    }

    //DB datatype mappings
    static mapping = {
        cognitoSub updatable: false
        version false
    }
}