package game

import opponent.Opponent

class OpponentSavedGame {

    Opponent opponent
    SavedGame savedGame

    static belongsTo = [opponent: Opponent, savedGame: SavedGame]

    static constraints = {
        opponent nullable: false
        savedGame nullable: false
    }

    static mapping = {
        table 'opponent_saved_game'
        id column: 'id', type: 'int'
        opponent column: 'opponent_id'
        savedGame column: 'saved_game_id'
        version false
    }
}