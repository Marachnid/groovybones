package user

import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import groovybones.SavedGame
import groovybones.User
import groovybones.savedGame.SavedGameService
import groovybones.user.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import spock.lang.Specification
import spock.lang.Stepwise
import util.SQLRunner
import javax.sql.DataSource

/**
 * Performs integration tests for User domain persistence operations
 * Each test transaction rolled back after execution
 */
@Integration
@Rollback
@Stepwise
class UserServiceIntegrationSpec extends Specification {
    private static final Logger log = LoggerFactory.getLogger(UserServiceIntegrationSpec)

    DataSource dataSource   //secrets file
    UserService userService
    SavedGameService savedGameService
    User user
    User existing
    ArrayList<User> users


    /**
     * refresh DB before tests run
     * entities generated in setup aren't rolled back - persist post-test
     * generate default records
     */
    void setup() {
        log.info('Running Integration Test Setup')
        new SQLRunner(dataSource).refreshDB()
    }


/*
  ======================= createUser(String cognitoSub, Long id) =======================
*/
    /** successfully create a new user and return user header */
    void "createUser() creates and returns new user ID and username"() {
        when: 'a new user is ready to be created'
        final String username = 'TEST-USER'
        final String cognitoSub = 'test123'

        and: 'the user is created, and an ID and Username are returned'
        final Map userHeader = userService.createUser(cognitoSub, username)
        existing = User.get(userHeader?.id)

        then: 'user headers match DB data'
        userHeader.id
        userHeader.username == username
        existing.id == userHeader.id
        existing.cognitoSub == cognitoSub
    }

    /** successfully create a new user with default values */
    void "createUser() creates user and sets default values"() {
        when: 'a new user is ready to be created'
        final String username = 'TEST-USER'
        final String cognitoSub = 'test123'

        and: 'the user is created'
        final Long id = userService.createUser(cognitoSub, username).id
        existing = User.get(id)

        then: 'default values for wins, losses, totalScore are created'
        existing.wins == 0
        existing.losses == 0
        existing.totalScore == 0
    }

    /** successfully return null on failed validation */
    void "createUser() fails to validate and returns null for invalid user"() {
        expect: 'null when adding blank cognitoSub or username'
        !userService.createUser('', 'test-new')
        !userService.createUser('1234567', '')
    }

    /** successfully return null for duplicate cognitoSubs */
    void "createUser() fails to validate"() {
        expect: 'a user with a duplicate cognitoSub fails to be created'
        userService.createUser('duplicateSub', 'test-user1')
        !userService.createUser('duplicateSub', 'test-user2')
    }


/*
  ======================= updateUser(Long id, Map newValues) =======================
*/
    /** successfully update updatable user values */
    void "updateUser() updates user"() {
        when: 'a user attempts to update updatable values'
        user = User.getAll()[0]
        final Map newValues = [username: 'newUser', wins: 3, losses: 5, totalScore: 200]

        and: 'the user is updated'
        final boolean updated = userService.updateUser(user.id, newValues)
        existing = User.get(user.id)

        then: 'true is returned and DB data matches new values'
        updated
        existing.username == newValues.username
        existing.wins == newValues.wins
        existing.losses == newValues.losses
        existing.totalScore == newValues.totalScore
    }

    /** successfully ignore update to cognitoSub */
    void "updateUser() fails to validate cognitoSub"() {
        when: 'a user has their cognitoSub changed'
        user = User.getAll()[0]
        final Map newValues = [cognitoSub: 'newSub']

        then: 'the update to cognitoSub is ignored'
        userService.updateUser(user.id, newValues)
        !User.findByCognitoSub(newValues.cognitoSub)
    }


/*
  ======================= getUserStats(Long id) =======================
*/
    /** successfully return user stats */
    void "getUserStats() returns wins, losses, and totalScore"() {
        when: 'a user retrieves their stats'
        user = User.getAll()[0]
        final Map stats = userService.getUserStats(user.id)

        then: 'stats equal DB data'
        stats.wins == user.wins
        stats.losses == user.losses
        stats.totalScore == user.totalScore
    }

    /** successfully return null stats for unfound user */
    void "getUserStats() returns null for unfound user"() {
        expect: 'an unfound id returns null'
        !userService.getUserStats(-1)
    }


/*
  ======================= getUserByCognitoSub(String sub, String username) =======================
*/
    /** successfully return user ID and username when found by cognitoSub */
    void "getUserByCognitoSub() finds user and returns ID and username"() {
        when: 'a user is found by cognitoSub'
        existing = User.getAll()[0]
        final String cognitoSub = existing.cognitoSub
        final Map userHeader = userService.getUserByCognitoSub(cognitoSub, null)

        then: 'existing cognitoSub is unchanged '
        existing.cognitoSub == User.get(existing.id).cognitoSub
        userHeader.id == existing.id
        userHeader.username == existing.username
    }

    /** successfully found user does not create new user */
    void "getUserByCognitoSub() does not create new user for found user"() {
        when: 'a user is found by cognitoSub'
        users = User.getAll()
        user = users[0]
        final int size = users.size()
        final String cognitoSub = user.cognitoSub

        userService.getUserByCognitoSub(cognitoSub, null)

        then: 'new user is not created'
        User.findAllByCognitoSub(cognitoSub).size() == size
    }

    /** failing to find user creates new user */
    void "getUserByCognitoSub() creates new user when user unfound"() {
        when: 'a user is ready to be queried by cognitoSub'
        final Map userValues = [cognitoSub: 'new-Sub', username: 'NEW-USER']
        final int size = User.getAll().size()

        and: 'the existing cognitoSub returns null'
        assert(!User.findByCognitoSub(userValues.cognitoSub))

        and: 'the cognitoSub is queried'
        Map userHeaders = userService.getUserByCognitoSub(userValues.cognitoSub, userValues.username)
        existing = User.get(userHeaders.id)

        then: 'a new user is created'
        User.getAll().size() == size + 1
        existing.id == userHeaders.id
        existing.username == userHeaders.username
        existing.cognitoSub == userValues.cognitoSub
        existing.username == userValues.username
    }

    /** successfully return null for invalid parameters */
    void "getUserByCognitoSub() returns null for invalid parameters"() {
        expect: 'null for invalid parameters'
        !userService.getUserByCognitoSub('', 'test-user')
        !userService.getUserByCognitoSub('xxxxxxx', '')
    }


/*
  ======================= deleteUser(Long id) =======================
*/
    /** successfully return true for deleting user */
    void "deleteUser() returns true for deleting a user"() {
        when: 'a user is found to be deleted'
        users = User.getAll()
        final int size = users.size()
        user = users[0]

        and: 'the user is deleted'
        final boolean deleted = userService.deleteUser(user.id)

        then: 'true is returned and the user is absent from DB records'
        deleted
        User.getAll().size() == size - 1
        !User.get(user.id)
    }

    /** successfully delete all saved games of user upon deleting user */
    void "deleteUser() deletes all user saved games"() {
        when: 'a user with a saved game is deleted'
        user = User.getAll()[0]
        final ArrayList userBoard = [[1,1],[2,3],[6]]
        final ArrayList opBoard = [[],[4,5],[3]]
        final Map sg = [userBoard: userBoard, opponentBoard: opBoard, userId: user.id, opponentId: 1, turn: 1]
        savedGameService.addSavedGame(sg)


        then: 'the user is deleted and no saved games exist for user'
        userService.deleteUser(user.id)
        SavedGame.findAllByUserId(user.id).size() == 0
    }

    /** successfully return false for user not found */
    void "deleteUser() returns false for user not found"() {
        expect: 'unfound IDs to return false'
        !userService.deleteUser(-1)
    }
}