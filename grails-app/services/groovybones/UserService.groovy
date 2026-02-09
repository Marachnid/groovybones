package groovybones

import grails.gorm.transactions.Transactional
import user.User

@Transactional
class UserService {

//    def serviceMethod() {
//
//    }

    def createUser(def firstName, def userName) {
        def user = new User(firstName: firstName, userName: userName)
        user.save(failOnError: true)
        return user

    }
}
