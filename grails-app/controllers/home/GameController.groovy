package home

import groovybones.GameBoard

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
        session['dice'] = new GameBoard().generateNumber()
        render(view: 'game')
    }
}