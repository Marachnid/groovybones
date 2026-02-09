package groovybones

import grails.gorm.transactions.Transactional
import user.User

@Transactional
class UserService {


    /**
     * creates a new DB entity
     * @param firstName user's first name
     * @param userName user's username
     * @return new user added to DB
     */
    def createUser(def firstName, def userName) {
        def user = new User(firstName: firstName, userName: userName)
        user.save(failOnError: true)
        return user
    }

    /**
     * deletes an existing DB entity
     * @param id id to be deleted
     * @return existing user to be deleted from DB
     */
    def deleteUser(int id) {
        def user = User.get(id)
        user.delete(failOnError: true)
    }


    /**
     * updates an existing entity's username
     * @param id user id to be updated
     * @param newName new user name
     * @return updated userName to be saved to DB
     */
    def updateUserName(int id, def newName) {
        def user = User.get(id)
        user.userName = newName
        user.save(failOnError: true)
        return user
    }
}
