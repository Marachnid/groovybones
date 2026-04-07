package groovybones.savedGame

import grails.gorm.transactions.Transactional
import groovybones.SavedGame
import groovybones.User
import groovy.json.JsonOutput


/**
 * Responsible for handling SavedGame persistence operations
 * Handles operations via id-to-update and maps of values to update
 * Can't use GORM one-to-many ORM when Opponent and User are separated via Opponent Webservice
 */
@Transactional
class SavedGameService {


    /**
     * add new savedGame DB entity to User
     * parses userBoard and opponentBoard into Strings for DB varchar
     * @param newGame map of new SavedGame values
     */
    Long addSavedGame(Map newGame) {
        log.info("SavedGameService addSavedGame() for ID: ${newGame.userId}")
        log.info("New game properties: ${newGame.toString()}")

        User user = User.get(newGame.userId as Long)
        if (!user) {
            log.info('User not found')
            return null
        }

        try {
            SavedGame savedGame = new SavedGame(newGame)
            savedGame.userBoard = JsonOutput.toJson(newGame.userBoard)
            savedGame.opponentBoard = JsonOutput.toJson(newGame.opponentBoard)

            savedGame.save(flush: true, failOnError: true)
            log.info("Saved game added, ID: $savedGame.id")
            savedGame.id

        } catch (e) {
            log.info("Invalid SavedGame: $e")
            null
        }
    }


    /**
     * retrieve list of savedGames sorted by ID descending
     * @param id userId to find savedGames for
     * @return list of savedGame maps sorted by ID
     */
    ArrayList<Map> getSavedGames(Long id) {
        log.info("SavedGameService getUserSavedGames for ID: $id")

        User user = User.get(id)
        if (!user) {
            log.info('User not found')
            return null
        }

        SavedGame.findAllByUserId(id).collect { sg ->
                [
                    id: sg.id,
                    userBoard: sg.userBoard,
                    opponentBoard: sg.opponentBoard,
                    turn: sg.turn,
                    userId: sg.userId,
                    opponentId: sg.opponentId
                ]
        }.sort {it.id}.reverse() as ArrayList<Map>
    }


    /**
     * delete all saved game records for a given user ID
     * @param id userId to delete for
     * @return true for games deleted, else false
     */
    boolean deleteAllSavedGames(Long id) {
        log.info("SavedGameService deleteAllSavedGames() for ID: $id")

        User user = User.get(id)
        if (!user) {
            log.info('User not found')
            return null
        }

        try {
            SavedGame.where {userId == id}.deleteAll()
            log.info('Saved games deleted')
            true
        } catch (e) {
            log.info("Saved games not deleted: $e")
            false
        }
    }


    /**
     * delete saved game by id
     * @param id SavedGame record to delete
     * @return true for deleted, else false for failure
     */
    boolean deleteSavedGame(Long id) {
        log.info("SavedGameService deleteSavedGame() for ID: $id")

        SavedGame savedGame = SavedGame.get(id)
        if (!savedGame) {
            log.info('Saved game not found')
            return false
        }

        try {
            SavedGame.get(id).delete(flush: true, failOnError: true)
            log.info("Saved game ID: $id deleted successfully")
            true
        } catch (e) {
            log.info("Deletion failed: $e")
            false
        }
    }
}