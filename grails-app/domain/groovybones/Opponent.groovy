package groovybones
/**
 * Represents an Opponent DB entity in MySQL table, 'opponent'
 * Implicitly maps Opponent class to 'opponent' table
 * One-to-Many relationship with SavedGame
 */
class Opponent {
    String username
    int difficulty      //Not expecting to exceed 1 opponent per difficulty tier for now
    int wins
    int losses
    int totalScore


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
        difficulty sqlType: 'TINYINT UNSIGNED', updateable: false
        version false
    }
}