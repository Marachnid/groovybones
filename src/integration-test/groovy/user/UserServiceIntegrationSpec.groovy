package user

import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import groovybones.User
import groovybones.user.UserService
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise
import util.SQLRunner
import javax.sql.DataSource

/** Carries out integration tests for user domain persistence operations */
@Integration
@Rollback
@Stepwise
class UserServiceIntegrationSpec extends Specification {
    @Shared
    DataSource dataSource

    User user
    User newUser
    UserService userService


    /** basic setup method to instantiate objects and refresh test schema*/
    void setup() {
        new SQLRunner(dataSource).refreshDB()

        userService = new UserService()
        user = User.get(1)
    }


    /** tests an updated user is returned  */
    void "updateUser() nullifies cognitoSub on original and reference user"() {
        when: 'a user is updated'
        newUser = userService.updateUser(user)

        then: 'null cognitoSub'
        newUser.cognitoSub == null
        user.cognitoSub == null
    }

    /** tests that user cognitoSub update is ignored */
    void "updateUser() ignores updates to cognitoSub"() {
        when: 'a user cognitoSub is changed'
        def oldSub = user.cognitoSub
        user.cognitoSub = 1000

        and: 'the user is updated'
        userService.updateUser(user)
        newUser = User.get(1)

        then: 'update to cognitoSub is ignored, old sub matches retrieved sub'
        newUser.cognitoSub != user.cognitoSub
        newUser.cognitoSub == oldSub
    }

    /** tests successfully updating user wins, losses, totalScore */
    void "updateUser() updates User successfully"() {
        when: 'user values are changed'
        user.losses = 5
        user.wins = 10
        user.totalScore = 100

        and: 'the user is updated'
        userService.updateUser(user)
        newUser = User.get(1)

        then: 'db record reflects the changes'
        newUser.losses == user.losses
        newUser.wins == user.wins
        newUser.totalScore == user.totalScore
    }

    /** tests an invalid id fails to find existing user */
    void "updateUser() fails to find id to update"() {
        when: 'user id is invalid'
        user.id = 100

        and: 'user tries to update'
        user = userService.updateUser(user)

        then: 'updateUser() throws null exception'
        thrown(NullPointerException)
    }

    /** tests overall successful update that results in cognitoSub ignored */
    void "updateUser() updates wins, losses, totalScore and ignores cognitoSub"() {
        when: 'user wins, losses, totalScore, and difficulty is updated'
        user.wins = 1
        user.losses = 1
        user.totalScore = 1
        user.cognitoSub = 1000

        and: 'user is updated'
        newUser = userService.updateUser(user)

        then: 'temp user should match wins, losses, totalScore, but not cognitoSub'
        newUser.id == user.id
        newUser.wins == user.wins
        newUser.losses == user.losses
        newUser.totalScore == user.totalScore
    }

    /** tests successfully creating a new user */
    void "createUser() creates a new user"() {
        when: 'a new user is created'
        String cognitoSub = '111111111'
        String username = 'Test-User1'
        user = userService.createUser(cognitoSub, username)

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
}