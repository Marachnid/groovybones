package savedGame

import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import groovybones.SavedGame
import groovybones.User
import groovybones.savedGame.SavedGameService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import spock.lang.Specification
import spock.lang.Stepwise
import util.SQLRunner
import javax.sql.DataSource


/**
 * Performs integration tests for SavedGame domain persistence operations
 * Each test transaction rolled back after execution
 */
@Integration
@Rollback
@Stepwise
class SavedGameServiceIntegrationSpec extends Specification {
    private static final Logger log = LoggerFactory.getLogger(SavedGameServiceIntegrationSpec)

    DataSource dataSource   //secrets file
    SavedGameService savedGameService
    User user

    ArrayList<Map> savedGames
    SavedGame savedGame
    Map sg

    ArrayList userBoard
    ArrayList opBoard


    /**
     * refresh DB before tests run
     * entities generated in setup aren't rolled back - persist post-test
     * generate default records
     */
    void setup() {
        log.info('Running Integration Test Setup')
        new SQLRunner(dataSource).refreshDB()

        userBoard = [[1,1],[2,3],[6]]
        opBoard = [[],[4,5],[3]]
        sg = [userBoard: userBoard, opponentBoard: opBoard, userId: 1, opponentId: 1, turn: 1]
    }


/*
  ======================= addSavedGame(Map newGame) =======================
*/
    /** successfully save game and return id */
    void "addSavedGame() saves game and returns id"() {
        when: 'a game is saved'
        final Long id = savedGameService.addSavedGame(sg)
        savedGame = SavedGame.get(id)

        then: 'DB data matches savedGame data'
        savedGame
        savedGame.userId == sg.userId
        savedGame.userBoard == sg.userBoard
        savedGame.opponentBoard == sg.opponentBoard
        savedGame.opponentId == sg.opponentId
    }

    /** successfully return null for invalid values */
    void "addSavedGame() returns null for invalid values"() {
        expect: 'missing IDs to return null'

        !savedGameService.addSavedGame(sg.tap {it.userId = null})
        !savedGameService.addSavedGame(sg.tap {it.opponentId = null})
        !savedGameService.addSavedGame(sg.tap {it.userBoard = null})
        !savedGameService.addSavedGame(sg.tap {it.opponentBoard = null})
        !savedGameService.addSavedGame(sg.tap {it.turn = null})
    }


/*
  ======================= getSavedGames(Long id) =======================
*/
    /** successfully return list of savedGames */
    void "getSavedGames() returns list of savedGames"() {
        when: 'saved games are added'
        final Long id = sg.userId as Long
        savedGameService.addSavedGame(sg)
        savedGameService.addSavedGame(sg.tap {it.turn = 10})
        savedGameService.addSavedGame(sg.tap {it.turn = 15})

        and: 'the games are retrieved'
        savedGames = savedGameService.getSavedGames(id)

        then: 'DB record size reflects added savedGames and savedGame data'
        savedGames.size() == 3
        savedGames[0].tap {it.remove('id')} == sg
    }

    /** successfully return functional gameboards */
    void "getSavedGames() returns functional game boards"() {
        when: 'game boards are persisted to DB'
        ArrayList newUserBoard = [[1],[2],[3]]
        ArrayList newOpBoard = [[1,2],[2,3],[3,4]]
        sg.userBoard = newUserBoard
        sg.opponentBoard = newOpBoard
        Long id = savedGameService.addSavedGame(sg)

        and: 'and the saved game is retrieved'
        savedGames = savedGameService.getSavedGames(sg.userId as Long)
        sg = savedGames.find {it.id == id}

        then: 'retrieved boards behave as 2d ArrayLists'
        sg
        sg.userBoard[0][0] == newUserBoard[0][0]
        sg.userBoard[1][0] == newUserBoard[1][0]
        sg.userBoard[2][0] == newUserBoard[2][0]
        sg.opponentBoard[0][1] == newOpBoard[0][1]
        sg.opponentBoard[1][1] == newOpBoard[1][1]
        sg.opponentBoard[2][1] == newOpBoard[2][1]
    }

    /** successfully return null for unfound users */
    void "getSavedGames() returns null for unfound user"() {
        expect: 'null for unfound users'
        !savedGameService.getSavedGames(-1)
    }


/*
  ======================= deleteAllSavedGames(Long id) =======================
*/
    /** successfully delete all saved games of user */
    void "deleteAllSavedGames() deletes all saved games of user"() {
        when: 'saved games are added'
        final Long id = sg.userId as Long
        savedGameService.addSavedGame(sg)
        savedGameService.addSavedGame(sg.tap {it.turn = 10})
        savedGameService.addSavedGame(sg.tap {it.turn = 15})

        and: 'all are deleted'

        then: 'the size of deleted games matches list size pre-delete'
        savedGameService.deleteAllSavedGames(id)
        SavedGame.findAllByUserId(id).size() == 0
    }

    /** successfully return null for user not found */
    void "deleteAllSavedGames() returns null for user not found"() {
        expect: 'unfound ids to return null'
        !savedGameService.deleteAllSavedGames(-1)
    }


/*
  ======================= deleteSavedGame(Long id) =======================
*/
    /** successfully delete saved game */
    void "deleteSavedGame() returns true for deleting saved game"() {
        when: 'saved game is added'
        user = User.get(1)
        sg.userId = user.id
        final Long id = savedGameService.addSavedGame(sg)
        final int size = SavedGame.findAllByUserId(sg.userId as Long).size()

        and: 'the game is deleted'
        boolean gameDeleted = savedGameService.deleteSavedGame(id)

        then: 'no saved games are found for user'
        gameDeleted
        SavedGame.findAllByUserId(sg.userId as Long).size() == size - 1
    }

    /** successfully return null for saved game not found */
    void "deleteAllSavedGames() returns null for saved game not found"() {
        expect: 'unfound ids to return null'
        !savedGameService.deleteSavedGame(-1)
    }
}
