package groovybones
/**
 * Represents SavedGame DB entity in MySQL table, 'saved_game'
 * Opponent one-to-many -> SavedGame
 * User one-to-many -> SavedGame
 * Flattened GORM structure - Opponent is behind an API,
 *  - can't directly access User and Opponent domain entities together
 *  - full ORM would need User moved to Webservice layer with Opponent or vice-versa
 *  - The front end uses modular loading of detached data and doesn't make use of ORM on the front end
 */
class SavedGame {
    Long userId
    Long opponentId
    ArrayList userBoard
    ArrayList opponentBoard
    int turn


    //enforce DB constraints
    static constraints = {
        userId nullable: false, blank: false, updatable: false
        opponentId nullable: false, blank: false, updatable: false
        turn blank: false, updatable: false
    }

    //define datatype mappings
    static mapping = {
        version false
    }
}