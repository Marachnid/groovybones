package groovybones
/**
 * Represents an Opponent DB entity in MySQL table, 'opponent'
 * Implicitly maps Opponent class to 'opponent' table
 * Flat one-to-Many relationship with SavedGame
 */
class Opponent {
    String username
    int difficulty      //Not expecting to exceed 1 opponent per difficulty tier for now
    int wins
    int losses
    int totalScore


    //enforce DB constraints
    static constraints = {
        username blank: false, maxSize: 25, updatable: false, unique: true
        difficulty inList: [1,2,3], updatable: false
        wins min: 0
        losses min: 0
        totalScore min: 0
    }

    //define datatype mappings
    static mapping = {
        username updateable: false
        difficulty sqlType: 'TINYINT UNSIGNED', updateable: false
        version false
    }
}