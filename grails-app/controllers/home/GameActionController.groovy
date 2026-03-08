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
        int columnIndex = params['col'] as int
        int dice = session['dice'] as int

        if (board.addNumber(columnIndex, dice)) {

            if (board.detectFullBoard()) redirect(controller: 'gameOver', action: 'gameOver')
            else redirect(controller: 'game', action: 'game')

        //TODO will add a session variable for 'column full' eventually
        } else {redirect(controller: 'game', action: 'game')}
    }



    //TODO this will likely be a sub-method within a player action/method
    def runOpponentBoard() {
        println 'GameActionController runBoard()'
        GameBoard board = session['opponentBoard'] as GameBoard
        int dice = session['dice'] as int

        if (board.runBoard(dice)) {session['opponentBoard'] = board

            if (board.detectFullBoard()) redirect(controller: 'gameOver', action: 'gameOver')
            else redirect(controller: 'game', action: 'game')

        } else {redirect(controller: 'gameOver', action: 'gameOver')}
    }
}