package home

import groovybones.GameBoard
import user.User

/**
 * Default homepage controller that renders game page view
 */
class GameController {

    /**
     * game page
     * @return render game.gsp
     */
    def game() {
        println 'GameController game()'

        if (session['mockID']) {
            session['user'] = User.get(session['mockID'] as int)
            session['userBoard'] = new GameBoard(boardName: session['user'].userName)
        }

        render(view: 'game')
    }
}