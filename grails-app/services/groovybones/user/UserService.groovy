package groovybones.user

import grails.gorm.transactions.Transactional
import groovybones.User
import groovybones.savedGame.SavedGameService

/**
 * Responsible for handling User persistence operations
 * Handles operations via id-to-update and maps of values to update
 */
@Transactional
class UserService {

    /**
     * retrieve user stats
     * @param id to find stats for
     * @return user stats map, else null
     */
    Map getUserStats(Long id) {
        log.info("UserService getUserStats for ID: $id")

        User user = User.get(id)
        if (!user) {
            log.info('User not found')
            return null
        }

        [wins: user.wins, losses: user.losses, totalScore: user.totalScore]
    }

    /**
     * finds user by cognitoSub
     * creates a new user if not
     * @param sub cognitoSub token
     * @return map of user ID and username, else null
     */
    Map getUserByCognitoSub(String sub, String username) {
        log.info("UserService getUserByCognitoSub()")

        User user = User.findByCognitoSub(sub)
        if (user) {
            log.info("User: ${user.id}, found")
            return [id: user.id, username: user.username]
        }
        else {
            log.info("User not found")
            return createUser(sub, username)
        }
    }

    /**
     * initializes and creates a new User entity with default wins/losses/totalScore
     * @param cognitoSub cognito profile token
     * @param username pulled from cognito Account ID
     * @return new userId and username as map
     */
    Map createUser(String cognitoSub, String username) {
        log.info('UserService createUser()')

        User user = new User(cognitoSub: cognitoSub, username: username,  wins: 0, losses: 0, totalScore: 0)

        try {
            user.save(flush: true, failOnError: true)
            log.info("New user created - ${user.id}, ${user.username}")

            [id: user.id, username: user.username]

        } catch (e) {
            log.info("New user couldn't be validated and/or saved: $e")
            null
        }
    }

    /**
     * updates user properties
     * @param id user entity to be updated
     * @param newValues map-based values to be updated
     * @return true if updated, else false
     */
    boolean updateUser(Long id, Map newValues) {
        log.info("UserService updateUser() for ID: $id")

        User user = User.get(id)
        if (!user) {
            log.info('User not found')
            return false
        }

        log.info("New values: ${newValues.toString()}")
        user.properties = newValues

        try {
            user.save(flush: true, failOnError: true)
            log.info("Update successful")
            true

        } catch (Exception e) {
            log.info("Update failed: $e")
            false
        }
    }

    /**
     * deletes an existing DB user if found
     * deletes savedGame children if they exist
     * @param id of User entity to be deleted
     * @return true if user exists and deletion does not return error, else false
     */
    boolean deleteUser(Long id) {
        log.info("UserService deleteUser() for ID: $id")

        User user = User.get(id)

        if (new SavedGameService().deleteAllSavedGames(id)) log.info("Saved games for user deleted")
        else {
            log.info("failed to delete saved games for user")
            return false
        }

        try {
            user.delete(flush: true, failOnError: true)
            log.info("Delete successful")
            true

        } catch (e) {
            log.info("Delete failed: $e")
            false
        }
    }
}