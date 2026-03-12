package home

import groovybones.GameBoard


/**
 * Responsible for controlling player and opponent turns and board actions
 */
class GameActionController {


    /**
     * default method, does not render anything at the moment
     * @return log statement
     */
    def gameAction() { println 'gameAction()' }


    /**
     * handles logic behind running a player turn
     * redirects player back to game with hint if trying to place in a full column
     * redirects player back to game if column add successful
     * redirects player to gameover if they fill their board
     * @return redirect to game or gameover
     */
    def runPlayerBoard() {
        println 'GameActionController runPlayerBoard()'

        GameBoard board = session['playerBoard'] as GameBoard
        int columnIndex = params['col'] as int
        int dice = session['dice'] as int
        session['columnFullHint'] = null                        //initialize/reinitialize to null to remove old message


        //if the dice can be added to the specific column and returns true
        if (board.addNumber(columnIndex, dice)) {

            //if the player's board is full after adding the dice, redirect to gameover page
            if (board.detectFullBoard()) redirect(controller: 'gameOver', action: 'gameOver')

            //if the player's board is not full, redirect back to game for opponent turn
            else redirect(controller: 'game', action: 'game')


        //if the number can't be added to the column, send the player back to game with a session hint
        } else {
            session['columnFullHint'] = 'column is full, try another'
            redirect(controller: 'game', action: 'game')
        }
    }


    /**
     * handles logic for game logic opponent turn
     * redirects back to game for player turn if add was successful
     * redirects to gameover if the board is filled
     * redirects to gameover if add is unsuccessful
     * @return redirect to game or gameover
     */
    def runOpponentBoard() {
        println 'GameActionController runBoard()'
        GameBoard board = session['opponentBoard'] as GameBoard
        int dice = session['dice'] as int

        //if the dice can be added to the opponent board and returns true
        if (board.runBoard(dice)) {session['opponentBoard'] = board

            //if the opponent board is full after adding, redirect to gameover
            if (board.detectFullBoard()) redirect(controller: 'gameOver', action: 'gameOver')

            //if the opponent's board is not full, redirect back to game for player's turn
            else redirect(controller: 'game', action: 'game')

        //if no dice can be added, redirect to gameover page (this condition "shouldn't" happen, but is a failsafe)
        } else {redirect(controller: 'gameOver', action: 'gameOver')}
    }
}