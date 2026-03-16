package groovybones

import grails.gorm.transactions.Transactional
import user.User

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
        user.save(failOnError: true)
        return user
    }


    /**
     * updates a User entity
     * @param user entity to be updated
     * @return updated User entity
     */
    User updateUser(User user) {
        user.save(failOnError: true)
        return user
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
