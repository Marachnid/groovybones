package home

import groovybones.UserService
import user.User

/**
 * Default homepage controller that renders index view
 * views/home/index.gsp is implicitly rendered
 */
class GameController {

    /**
     * single-line method to print a test message and render game
     * @return render game.gsp
     */
    def game() {
        println 'GameController game()'

        User user = null
        if (session['mockID']) {
            user = User.get(session['mockID'] as int)
        }


        [user: user]
    }
}
