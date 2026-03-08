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

    def addNumber() {
        println 'ADD NUMBER CALLED'
        println params['col']
        redirect(controller: 'game', action: 'game')
    }


    //TODO this will likely be a sub-method within a player action/method
    def runBoard() {
        gameAction()
        GameBoard board = session['userBoard'] as GameBoard

        if (board.runBoard(board.generateNumber())) {session['userBoard'] = board

            if (board.detectFullBoard()) redirect(controller: 'gameOver', action: 'gameOver')
            else redirect(controller: 'game', action: 'game')

        } else {redirect(controller: 'gameOver', action: 'gameOver')}
    }
}