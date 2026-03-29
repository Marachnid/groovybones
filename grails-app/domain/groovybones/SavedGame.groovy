package groovybones
/**
 * Represents SavedGame DB entity in MySQL table, 'saved_game'
 * Opponent one-to-many -> SavedGame
 * User one-to-many -> SavedGame
 */
class SavedGame {
    String userBoard
    String opponentBoard
    int turn

    User user
    Opponent opponent

    //contains User and Opponent foreign keys
    static belongsTo = [user: User, opponent: Opponent]

    //enforce DB constraints
    static constraints = {
        userBoard maxSize: 60, nullable: false
        opponentBoard maxSize: 60, nullable: false
        turn nullable: false
        user nullable: false
        opponent nullable: false
    }

    //define datatype mappings
    static mapping = {
        table 'saved_game'
        user column: 'user_id'
        opponent column: 'opponent_id'
        version false
    }

    /**
     * utility method for returning opponent as a map of values
     * @return opponent as map
     */
    Map returnAsMap() {
        [
            id: id,
            userBoard: userBoard,
            opponentBoard: opponentBoard,
            turn: turn,
            opponentId: opponentId,
            userId: userId,
            opponent: opponent,
            user: user
        ]
    }
}