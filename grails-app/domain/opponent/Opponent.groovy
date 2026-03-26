package opponent

import game.SavedGame

/**
 * Represents an Opponent DB entity in MySQL table, 'opponent'
 * Implicitly maps Opponent class to 'opponent' table
 * GORM domain classes have persistence built-in, no DAO needed
 */
class Opponent {
    String username
    int difficulty      //Not expecting to exceed 1 opponent per difficulty tier for now
    int wins
    int losses
    int totalScore


    //foreign key - one to many relationship
    static hasMany = [savedGames: SavedGame]

    //enforce DB constraints
    static constraints = {
        username nullable: false, blank: false, maxSize: 25, updatable: false, unique: true
        difficulty nullable: false, inList: [1,2,3], updatable: false
        wins nullable: false, min: 0
        losses nullable: false, min: 0
        totalScore nullable: false, min: 0
    }

    //define datatype mappings
    static mapping = {
        username updateable: false
        difficulty updateable: false
        version false
        difficulty sqlType: 'TINYINT UNSIGNED'
    }

    /**
     * utility method for returning opponent as a map of values
     * @return opponent as map
     */
    Map returnAsMap() {
        [id: id, username: username, difficulty: difficulty, wins: wins, losses: losses, totalScore: totalScore]
    }
}