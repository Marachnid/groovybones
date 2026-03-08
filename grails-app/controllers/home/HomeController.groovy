package home

import groovybones.GameBoard
import user.User

/**
 * Default homepage controller that renders index view
 * views/home/index.gsp is implicitly rendered
 */
class HomeController {

    /**
     * single-line method to print a test message and render index
     * @return render index.gsp
     */
    def index() {
        println 'HomeController index()'

        session['mockID'] = 1

        if (session['mockID']) {
            session['user'] = User.get(session['mockID'] as int)
            session['userBoard'] = new GameBoard(boardName: session['user'].userName)
        }
        render(view: 'index')
    }
}