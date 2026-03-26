package game

import opponent.Opponent
import user.User

class SavedGame {

    String userBoard
    String opponentBoard
    int turn

    User user
    Opponent opponent

    static belongsTo = [user: User, opponent: Opponent]

    static constraints = {
        userBoard maxSize: 60, nullable: false
        opponentBoard maxSize: 60, nullable: false
        turn nullable: false
        user nullable: false
        opponent nullable: false
    }

    static mapping = {
        table 'saved_game'
        user column: 'user_id'
        opponent column: 'opponent_id'
        version false
    }
}