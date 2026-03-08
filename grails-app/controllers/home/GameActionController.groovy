package home

import groovybones.GameBoard

/**
 *
 */
class GameActionController {



    def gameAction() { println 'gameAction()' }

    def runPlayerBoard() {
        println 'GameActionController addNumber()'
        GameBoard board = session['playerBoard'] as GameBoard

        if (board.addNumber(params['col'] as int, board.generateNumber())) {

            if (board.detectFullBoard()) redirect(controller: 'gameOver', action: 'gameOver')
            else redirect(controller: 'game', action: 'game')

        //TODO will add a session variable for 'column full' eventually
        } else {redirect(controller: 'game', action: 'game')}
    }



    //TODO this will likely be a sub-method within a player action/method
    def runOpponentBoard() {
        println 'GameActionController runBoard()'
        GameBoard board = session['opponentBoard'] as GameBoard

        if (board.runBoard(board.generateNumber())) {session['opponentBoard'] = board

            if (board.detectFullBoard()) redirect(controller: 'gameOver', action: 'gameOver')
            else redirect(controller: 'game', action: 'game')

        } else {redirect(controller: 'gameOver', action: 'gameOver')}
    }
}