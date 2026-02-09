package home

import groovybones.UserService
import user.User

/**
 * Performs a GORM select-all query on User domain entity and displays results
 * renders display with an attached model of results - List<User>
 * views/display/display.gsp is implicitly rendered
 */
class DisplayController {

    UserService userService

    /**
     * single-line method to define model of User objects as a list and render display
     * @return List<User> results, render display.gsp
     */
    def display() {
        userService.createUser('test', 'testUser')

        [users: User.list()]
    }
}
