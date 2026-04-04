package game

import groovybones.game.GameBoard
import groovybones.game.OpponentActions


/**
 * Responsible for controlling user and opponent routing based on GameBoard board actions/results
 */
class GameActionController {

    /**
     * handles logic behind running a user turn
     * redirects user back to game with hint if trying to place in a full column
     * redirects user back to game if column add successful
     * redirects user to gameOver if they fill their board
     * @return redirect to game or gameOver
     */
    def runPlayerBoard() {
        log.info('GameActionController runPlayerBoard()')

        //allows opponent to run autonomously after user turn is finished
        session['userTurn'] = false

        final GameBoard userBoard = session['userBoard'] as GameBoard
        final GameBoard opponentBoard = session['opponentBoard'] as GameBoard
        final int columnIndex = params['col'] as int         //retrieved via JS event listener calling controller path
        final int dice = session['dice'] as int

        log.info("Dice: $dice")
        log.info("User column selected: $columnIndex")


        //if the dice can be added to the specific column, delete matching dice in same opponent column
        if (userBoard.addNumber(columnIndex, dice)) {
            opponentBoard.deleteNumber(columnIndex, dice)
            log.info("dice: $dice added to column: $columnIndex")

            //redirect to gameOver if board is full after placing, else redirect to game:turnManager
            if (userBoard.detectFullBoard()) redirect(controller: 'gameOver', action: 'gameOverAction')
            else redirect(controller: 'game', action: 'turnManager')

        //redirect to game with hint if column already full
        } else {
            log.info('column already full')
            redirect(controller: 'game', action: 'game')
        }
    }


    /**
     * handles logic for game logic opponent turn
     * redirects back to game for user turn if add was successful
     * redirects to gameOver if the board is filled
     * redirects to gameOver if add is unsuccessful for any reason ("shouldn't happen")
     * @return redirect to game or gameOver
     */
    def runOpponentBoard() {
        log.info('GameActionController runOpponentBoard()')

        //allows player to execute their turn after opponent's
        session['playerTurn'] = true

        final OpponentActions opponentActions = session['opponentActions'] as OpponentActions
        final GameBoard opponentBoard = session['opponentBoard'] as GameBoard
        final int dice = session['dice'] as int

        //if the dice can be added to the opponent board and returns true
        if (opponentActions.opponentOrchestrator(dice)) {
            log.info("opponent placed dice: $dice")

            //if the opponent board is full after adding, redirect to gameOver
            if (opponentBoard.detectFullBoard()) redirect(controller: 'gameOver', action: 'gameOverAction')

            //if the opponent's board is not full, redirect back to game for user's turn
            else redirect(controller: 'game', action: 'turnManager')

            //if no dice can be added, redirect to gameOver page ("shouldn't" happen, but is a failsafe)
        } else {redirect(controller: 'gameOver', action: 'gameOverAction')}
    }
}