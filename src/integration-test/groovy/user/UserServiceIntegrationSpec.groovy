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

        //sample user
        userService.createUser('123', 'TEST-USER')

        //sample savedGame
        User user = userService.getUserById(1)
        Opponent opponent = Opponent.get(1)
        SavedGame savedGame = new SavedGame(user: user, opponent: opponent, userBoard: 'user-board', opponentBoard: 'opponent-board', turn: 1)
        userService.addSavedGame(user, savedGame)
    }


    /*
       ======================= getUserById() =======================
     */
    /** tests successfully returning a user by ID with null in-memory cognitoSub */
    void "getUserById() returns User by ID with null cognitoSub"() {
        when: 'a user is retrieved'
        final def id = 1
        User user = userService.getUserById(id)

        then: 'the user is not null and cognitoSub is nullified'
        user
        !user.cognitoSub
        user.id == id
    }

    /** tests successfully returning a null user if ID not found*/
    void "getById() returns null user if ID not found"() {
        expect: 'user queried with unfound ID returns null'
        !userService.getUserById(10)
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

    /** tests successfully returning false if fails to find user */
    void "updateUser() returns false for not ID found"() {
        when: 'an unknown user ID attempts to update'
        User user = userService.getUserById(1)
        user.id = 100

        then: 'update returns false'
        !userService.updateUser(user)
    }

    /** tests successfully returning false if update fails to validate */
    void "updateUser() returns false for validation failure"() {
        when: 'a user has an invalid property'
        User user = userService.getUserById(1)
        user.wins = -10

        then: 'update returns false'
        !userService.updateUser(user)
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
    void "updateUser() DB updates match in-memory updates"() {
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


    /*
      ======================= createUser() =======================
    */
    /** tests successfully creating a new user with default values */
    void "createUser() creates and returns new user with default values"() {
        when: 'a new user is created'
        String username = 'TEST-USER-2'
        User user = userService.createUser('test-sub', username)

        then: 'new user is initialized with default values and nullified in-memory cognitoSub'
        user.id
        user.username == username
        user.wins == 0
        user.losses == 0
        user.totalScore == 0
        !user.cognitoSub
    }

    /** tests successfully returning null user on failed validation */
    void "createUser() returns null for invalid user"() {
        expect: 'null when adding blank cognitoSub or username'
        !userService.createUser('', 'test-new')
        !userService.createUser('1234567', '')
    }


    /*
      ======================= deleteUser() =======================
    */
    /** tests successfully deleting an existing user */
    void "deleteUser() returns true and removes user record"() {
        when: 'a user is ready to be deleted'
        long id = User.findAll()[0].id

        then: 'deletion returns true, null record in db'
        userService.deleteUser(id)
        !User.get(id)
    }

    /** tests successfully returning false if user not found */
    void "deleteUser() returns false for user not found"() {
        expect: 'an unfound user to return false'
        !userService.deleteUser(100)
    }

    /** tests successfully removing savedGame db children */
    void "deleteUser() deletes child savedGames"() {
        when: 'a user is deleted'
        long id = 1
        userService.deleteUser(id)

        then: 'no SavedGame entities with userId exist in db'
        !SavedGame.findAll(userId: id)
    }


    /*
      ======================= addSavedGame() =======================
     */
    /** tests successfully adding a new saved game */
    void "addSavedGame() adds a new SavedGame to user"() {
        when: 'a saved game body is built'
        User user = userService.getUserById(1)
        Opponent opponent = Opponent.get(1)
        Map sg = [user: user, opponent: opponent, userBoard: 'user-board', opponentBoard: 'opponent-board', turn: 7]

        and: 'the saved game is added and returned'
        int oldSize = user.savedGames.size()
        SavedGame savedGame = userService.addSavedGame(user, new SavedGame(sg))

        then: 'an ID is attached and created values match original values'
        savedGame.id
        User.get(1).savedGames.size() == oldSize + 1
        savedGame.userId == sg.user.id
        savedGame.opponentId == sg.opponent.id
        savedGame.userBoard == sg.userBoard
        savedGame.opponentBoard == sg.opponentBoard
        savedGame.turn == sg.turn
    }

    /** tests successfully returning null for failed savedGame add */
    void "addSavedGame() returns null on adding bad values"() {
        when: 'a saved game attempts to be added'
        User user = userService.getUserById(1)
        Opponent opponent = Opponent.get(1)
        Map sg = [user: user, opponent: opponent, userBoard: 'user-board', opponentBoard: 'opponent-board', turn: 7]

        then: 'return null for failed adds'
        !userService.addSavedGame(user, new SavedGame(sg).tap { it.opponent.id = null })
        !userService.addSavedGame(user, new SavedGame(sg).tap { it.user.id = null })
        !userService.addSavedGame(user, new SavedGame(sg).tap { it.user.totalScore = -100 })
        !userService.addSavedGame(user, new SavedGame(sg).tap { it.opponentBoard = null })
        !userService.addSavedGame(user, new SavedGame(sg).tap { it.userBoard = null })
    }


    /*
      ======================= deleteSavedGame() =======================
    */
    /** tests successfully deleting a saved game */
    void "deleteSavedGame() deletes a savedGame"() {
        when: 'a user finds a savedGame to delete'
        User user = userService.getUserById(1)
        SavedGame savedGame = user.savedGames[0]

        and: 'and they delete it'
        savedGame = userService.deleteSavedGame(user, savedGame)

        then: 'deleted savedGame ID not found in-memory or in db'
        !user.savedGames.find {it.id == savedGame.id}
        !SavedGame.get(savedGame.id)
    }

    /** tests successfully retuning null for failed deletions */
    void "deleteSavedGame() returns null if fail to delete"() {
        when: 'a user finds a savedGame to delete'
        User user = userService.getUserById(1)
        SavedGame savedGame = User.get(1).savedGames[0]

        then: 'return null for failed delete attempts'
        !userService.deleteSavedGame(user.tap{it.id = 100}, savedGame)
        !userService.deleteSavedGame(user.tap{it.totalScore = -10}, savedGame)
        !userService.deleteSavedGame(user, savedGame.tap {it.id = 100})
    }
}