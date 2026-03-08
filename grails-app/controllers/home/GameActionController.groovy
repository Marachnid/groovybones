package home

import groovybones.GameBoard

/**
 * Default homepage controller that renders index view
 * views/home/index.gsp is implicitly rendered
 */
class GameActionController {



    def gameAction() {
        println 'gameAction()'
    }


    def runBoard() {

        gameAction()
        GameBoard board = session['userBoard'] as GameBoard

        if (board.runBoard(board.generateNumber())) {
            session['userBoard'] = board
            redirect(controller: 'game', action: 'game')

        } else {
            redirect(controller: 'gameOver', action: 'gameOver')
        }
    }
}
