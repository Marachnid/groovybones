package groovybones.user

import grails.gorm.transactions.Transactional
import groovybones.User

/**
 * Responsible for handling User persistence operations
 */
@Transactional
class UserService {

    /**
     * initializes and creates a new User entity
     * @param cognitoSub token parsed from Cognito account ID
     * @param username username pulled from Account ID
     * @return new user with pre-initialized wins/losses/totalScore = 0
     */
    User createUser(String cognitoSub, String username) {
        User user = new User(cognitoSub: cognitoSub, username: username,  wins: 0, losses: 0, totalScore: 0)

        if (user.validate()) {
            user.save(flush: true, failOnError: true)
            user.cognitoSub = null                  //nullify cognito sub on return - in case of reserialization on accident
        } else user = null

        user
    }

    /**
     * updates a User entity
     * @param user entity to be updated
     * @return updated user entity
     */
    User updateUser(User user) {
        User existing = null

        if (user.validate()) {
            existing = User.get(user.id)
            existing.username = user.username
            existing.wins = user.wins
            existing.losses = user.losses
            existing.totalScore = user.totalScore
            existing.save(flush: true, failOnError: true)
            existing.cognitoSub = null                  //nullify cognitoSub on return - in case of reserialization on accident
        }
        user.cognitoSub = null                      //nullify original user cognitoSub
        existing
    }

    /**
     * deletes an existing DB entity
     * @param id id to be deleted
     * @return existing user to be deleted from DB
     */
    def deleteUser(def id) {
        def user = User.get(id as int)
        user.delete(failOnError: true)
    }
}
