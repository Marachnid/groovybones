package home

import groovybones.GameBoard
import groovybones.OpponentActions


/**
 * Responsible for controlling player and opponent routing based on GameBoard board actions/results
 */
class GameActionController {

    /**
     * handles logic behind running a player turn
     * redirects player back to game with hint if trying to place in a full column
     * redirects player back to game if column add successful
     * redirects player to gameOver if they fill their board
     * @return redirect to game or gameOver
     */
    def runPlayerBoard() {
        log.info('GameActionController runPlayerBoard()')

        //allows opponent to run autonomously after player turn is finished
        session['playerTurn'] = false

        GameBoard playerBoard = session['playerBoard'] as GameBoard
        GameBoard opponentBoard = session['opponentBoard'] as GameBoard
        int columnIndex = params['col'] as int
        int dice = session['dice'] as int
        session['columnFullHint'] = null        //initialize/reinitialize to null to remove old message

        log.info("Dice: $dice")
        log.info("Player column selected: $columnIndex")


        //if the dice can be added to the specific column, delete matching dice in same opponent column
        if (playerBoard.addNumber(columnIndex, dice)) {
            opponentBoard.deleteNumber(columnIndex, dice)
            log.info("dice: $dice added to column: $columnIndex")

            //redirect to gameOver if board is full after placing, else redirect to game:gameOrchestrator
            if (playerBoard.detectFullBoard()) redirect(controller: 'gameOver', action: 'gameOver')
            else redirect(controller: 'game', action: 'gameOrchestrator')

        //redirect to game with hint if column already full
        } else {
            session['columnFullHint'] = 'column is full, try another'

            log.info('column already full')
            redirect(controller: 'game', action: 'game')
        }
    }


    /**
     * handles logic for game logic opponent turn
     * redirects back to game for player turn if add was successful
     * redirects to gameOver if the board is filled
     * redirects to gameOver if add is unsuccessful for any reason ("shouldn't happen")
     * @return redirect to game or gameOver
     */
    def runOpponentBoard() {
        log.info('GameActionController runOpponentBoard()')

        //allows player to execute their turn after opponent's
        session['playerTurn'] = true

        OpponentActions opponentActions = session['opponentActions'] as OpponentActions
        GameBoard opponentBoard = session['opponentBoard'] as GameBoard
        int dice = session['dice'] as int

        //if the dice can be added to the opponent board and returns true
        if (opponentActions.opponentOrchestrator(dice)) {
            log.info("opponent placed dice: $dice")

            //if the opponent board is full after adding, redirect to gameOver
            if (opponentBoard.detectFullBoard()) redirect(controller: 'gameOver', action: 'gameOver')

            //if the opponent's board is not full, redirect back to game for player's turn
            else redirect(controller: 'game', action: 'gameOrchestrator')

            //if no dice can be added, redirect to gameOver page ("shouldn't" happen, but is a failsafe)
        } else {redirect(controller: 'gameOver', action: 'gameOver')}
    }
}