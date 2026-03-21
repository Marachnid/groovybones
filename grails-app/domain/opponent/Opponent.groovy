package opponent

/**
 * Represents an Opponent DB entity in MySQL table, 'opponent'
 * Implicitly maps Opponent class to 'opponent' table
 * GORM domain classes have persistence built-in, no DAO needed
 */
class Opponent {
    String username
    int difficulty      //TODO difficulty as is here wouldn't be normalized to 3rd degree, meh for now
    int wins
    int losses
    int totalScore

    //enforce DB constraints
    static constraints = {
        username nullable: false, blank: false, maxSize: 25
        difficulty nullable: false, inList: [1,2,3]
        wins nullable: false, min: 0
        losses nullable: false, min: 0
        totalScore nullable: false, min: 0
    }

    //define datatype mappings
    static mapping = {
        version false
        difficulty sqlType: 'TINYINT UNSIGNED'
    }
}
