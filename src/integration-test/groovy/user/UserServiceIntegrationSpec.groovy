package user

import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import groovybones.Opponent
import groovybones.SavedGame
import groovybones.User
import groovybones.user.UserService
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise
import util.SQLRunner
import javax.sql.DataSource

/** Performs integration tests for user domain persistence operations */
@Integration
@Rollback
@Stepwise
class UserServiceIntegrationSpec extends Specification {
    @Shared
    DataSource dataSource
    UserService userService = new UserService()


    /** basic setup method to instantiate objects and refresh db schema*/
    void setup() {
        new SQLRunner(dataSource).refreshDB()
        User user = userService.createUser('123', 'TEST-USER')
        Opponent opponent = Opponent.get(1)

        Map savedGame1 = [userBoard: 'user-board', opponentBoard: 'opponent-board', turn: 3, user: user, opponent: opponent]
        Map savedGame2 = [userBoard: 'board', opponentBoard: 'board', turn: 5, user: user, opponent: opponent]
        SavedGame sg1 = new SavedGame(savedGame1)
        SavedGame sg2 = new SavedGame(savedGame2)
        user.addToSavedGames(sg1)
        user.addToSavedGames(sg2)
    }


    /*
       ======================= getByUserId() =======================
     */
    /** tests successfully returning a user by ID with null in-memory cognitoSub */
    void "getById() returns User by ID with null cognitoSub"() {
        when: 'a user is retrieved'
        final def id = 1
        User user = userService.getUserById(id)

        then: 'the user is not null and cognitoSub is nullified'
        user
        user.cognitoSub == null
        user.id == id
    }

    /** tests successfully returning a null user if ID not found*/
    void "getById() returns null user if ID not found"() {
        expect: 'user queried with unfound ID returns null'
        new UserService().getUserById(10) == null
    }


    /*
      ======================= updateUser() =======================
    */
    /** tests successfully updating user without instantiating cognitoSub */
    void "updateUser() returns true on successful update"() {
        when: 'user is found for update'
        User user = userService.getUserById(1)

        then: 'update returns true and cognitoSub is null'
        userService.updateUser(user)
        user.cognitoSub == null
    }

    /** tests successfully ignoring update to cognitoSub */
    void "updateUser() ignores updates to cognitoSub"() {
        when: 'a user cognitoSub changes'
        String cognitoSub = User.get(1).cognitoSub
        User user = userService.getUserById(1)
        user.cognitoSub = 'BAD-KEY'

        and: 'we update the user'
        userService.updateUser(user)

        then: 'the cognitoSub remains unchanged'
        User.get(1).cognitoSub == cognitoSub
    }

    /** tests successfully updating user wins, losses, totalScore */
    void "updateUser() updates User successfully"() {
        when: 'user values are changed'
        User user = userService.getUserById(1)

        def newValues = [wins: 5, losses: 10, totalScore: 100]
        user.wins = newValues.wins
        user.losses = newValues.losses
        user.totalScore = newValues.totalScore

        and: 'the user is updated'
        userService.updateUser(user)

        and: 'we grab the user from the DB to verify'
        user = userService.getUserById(user.id)

        then: 'db record reflects the changes'
        user.wins == newValues.wins
        user.losses == newValues.losses
        user.totalScore == newValues.totalScore
    }

    /** tests successfully returning false if fails to find user */
    void "updateUser() fails to find user"() {
        when: 'an unknown user ID attempts to update'
        User user = userService.getUserById(1)
        user.id = 100

        then: 'update returns false'
        !userService.updateUser(user)
    }

    /** tests successfully returning false if update fails to validate */
    void "updateUser() fails to validate"() {
        when: 'a user has an invalid property'
        User user = userService.getUserById(1)
        user.wins = -10

        then: 'update returns false'
        !userService.updateUser(user)
    }

    /** tests successfully creating a new user */
    void "createUser() creates a new user"() {
        when: 'a new user is created'
        User user = userService.createUser('test-sub', 'TEST-USER-2')

        then: 'new user is initialized with default values and nullified in-memory cognitoSub'
        user.id != null
        user.username == username
        user.cognitoSub == null
        user.wins >= 0
        user.losses >= 0
        user.totalScore >= 0
    }

    /** tests successfully returning null user on failed creation */
    void "createUser() returns null user on failed "() {
        when: 'a new user is invalid'
        String cognitoSub = ''
        String username = 'test-1'
        user = userService.createUser(cognitoSub, username)

        then: 'new user is initialized with default values and nullified in-memory cognitoSub'
        user == null
    }

    /** tests successfully deleting an existing user */
    void "deleteUser() removes user record"() {
        when: 'a user is deleted'
        userService.deleteUser(User.get(1).id)

        then: 'user no longer exists'
        User.get(1) == null
    }

    /** tests throwing a null exception failing to delete an existing user */
    void "deleteUser() removes user record"() {
        when: 'a user attempts to be deleted'
        userService.deleteUser(User.get(199).id)

        then: 'null exception thrown for ID not found'
        thrown(NullPointerException)
    }

    /*
      ======================= User-driven SavedGame Operations =======================
     */
    /** tests successfully adding a new saved game */
    void "createSavedGame() adds a new SavedGame to user"() {
        when: 'a saved game is ready to be created'
        user = User.get(1)
        opponent = Opponent.get(1)
        int size = user.savedGames.size()

        and: 'saved game created'
        userService.createSavedGame(user, opponent, userBoard, opponentBoard, 5)

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
        userService.createSavedGame(user, opponent, '', opponentBoard, 5)

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
        userService.deleteSavedGame(user, savedGame)

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
        userService.deleteSavedGame(user, savedGame)

        then: 'null exception is thrown'
        thrown(NullPointerException)
    }
}