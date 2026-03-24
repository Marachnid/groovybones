package opponent

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

    //enforce DB constraints
    static constraints = {
        username nullable: false, blank: false, maxSize: 25, updateable: false
        difficulty nullable: false, inList: [1,2,3], updateable: false
        wins nullable: false, min: 0
        losses nullable: false, min: 0
        totalScore nullable: false, min: 0
    }

    //define datatype mappings
    static mapping = {
        version false
        difficulty sqlType: 'TINYINT UNSIGNED'
    }


    /**
     * handles updating an existing opponent's wins, losses, and totalScore
     * @return updated opponent
     */
    def updateOpponent() {
        Opponent existing = Opponent.findByUsername('Chug Chug')

        existing.wins = wins as int
        existing.losses = losses as int
        existing.totalScore = totalScore as int
        existing.save(failOnError: true)
    }


    /**
     * utility method for returning opponent as a map of values
     * @return opponent as map
     */
    Map opponentAsMap() {
        [username: username, difficulty: difficulty, wins: wins, losses: losses, totalScore: totalScore]
    }
}