package user

import game.UserSavedGame

/**
 * Represents a User DB entity in MySQL table, 'user'
 * Implicitly maps User class to 'user' table
 * GORM domain classes have persistence built-in, no DAO needed
 */
class User {
    String cognitoSub
    String username
    int wins
    int losses
    int totalScore


    static hasMany = [savedGames: UserSavedGame]

    //enforce DB constraints
    static constraints = {
        cognitoSub nullable: false, blank: false, maxSize: 36, updateable: false
        username nullable: false, blank: false, maxSize: 25
        wins nullable: false, min: 0
        losses nullable: false, min: 0
        totalScore nullable: false, min: 0
    }

    //define datatype mappings
    static mapping = { version false }
}