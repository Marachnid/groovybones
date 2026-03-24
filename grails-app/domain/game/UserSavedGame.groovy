package game

import user.User

class UserSavedGame {

    User user
    SavedGame savedGame

    static belongsTo = [user: User, savedGame: SavedGame]

    static constraints = {
        user nullable: false
        savedGame nullable: false
    }

    static mapping = {
        table 'user_saved_game'
        id column: 'id', type: 'int'
        user column: 'user_id'
        savedGame column: 'saved_game_id'
        version false
    }
}