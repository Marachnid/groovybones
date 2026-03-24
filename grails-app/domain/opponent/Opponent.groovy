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
     * convenience method to print object for validation
     * @return prints object
     */
    def printOpponent() {
        println "username: $username && difficulty: $difficulty && wins: $wins && losses: $losses && totalScore: $totalScore"
    }
}