package user

import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import groovybones.Opponent
import groovybones.SavedGame
import groovybones.User
import groovybones.user.UserSavedGameService
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise
import util.SQLRunner

import javax.sql.DataSource

/** Carries out integration tests for user domain persistence operations */
@Integration
@Rollback
@Stepwise
class UserSavedGameIntegrationSpec extends Specification {
    @Shared
    DataSource dataSource

    User user
    UserSavedGameService usgs
    SavedGame savedGame
    Opponent opponent

    String userBoard = 'INTEGRATION TEST - USERBOARD'
    String opponentBoard = 'INTEGRATION TEST - OPBOARD'


    /** basic setup method to instantiate objects and refresh test schema*/
    void setup() {
        new SQLRunner(dataSource).refreshDB()
        usgs = new UserSavedGameService()
    }

    /** tests successfully adding a new saved game */
    void "createSavedGame() adds a new SavedGame to user"() {
        when: 'a saved game is ready to be created'
        user = User.get(1)
        opponent = Opponent.get(1)
        int size = user.savedGames.size()

        and: 'saved game created'
        usgs.createSavedGame(user, opponent, userBoard, opponentBoard, 5)

        and: 'saved game is found'
        savedGame = user.savedGames.find {
            it.userBoard == userBoard &&
            it.opponentBoard == opponentBoard &&
            it.turn == 5
        }

        then: 'the found game is not null, savedGames size is increased by 1'
        savedGame != null
        user.savedGames.size() == size + 1
    }

    /** tests throwing null exception for missing items */
    void "createSavedGame() returns null exception on missing value"() {
        when: 'a user tries to save a game with a missing value'
        user = User.get(1)
        usgs.createSavedGame(user, opponent, '', opponentBoard, 5)

        then: 'null exception is thrown'
        thrown(NullPointerException)
    }

    /** tests successfully deleting a saved game */
    void "deleteSavedGame() deletes a savedGame"() {
        when: 'a user finds a savedGame to delete'
        user = User.get(1)
        savedGame = user.savedGames[0]
        int size = user.savedGames.size()

        and: 'and they delete it'
        usgs.deleteSavedGame(user, savedGame)

        then: 'their savedGames list size--, deleted savedGame ID not found'
        user.savedGames.size() == size - 1
        user.savedGames.find {it.id == savedGame.id} == null
    }

    /** tests throwing null exception for missing items */
    void "deleteSavedGame() returns null exception user not found"() {
        when: 'a user tries to delete a game with a missing value'
        user = User.get(1)
        savedGame = user.savedGames[0]
        user.id = 0
        usgs.deleteSavedGame(user, savedGame)

        then: 'null exception is thrown'
        thrown(NullPointerException)
    }
}