package home

import groovybones.GameBoard
import groovybones.OpponentActions
import user.User

/**
 * Default homepage controller that renders index view
 * views/home/index.gsp is implicitly rendered
 */
class GameSetupController {

    /**
     * single-line method to print a test message and render gameSetup
     * @return render gameSetup
     */
    def gameSetup() { println 'GameSetup gameSetup()' }

    /**
     * initializes session variables to setup player and opponent game boards
     * activates gameOrchestrator for player vs. opponent turn sequencing
     * @return gameOrchestrator()
     */
    def gameInitialization() {
        println 'GameSetup gameInitialization()'

        //instantiate player session GameBoard
        session['playerBoard'] = new GameBoard()

        //instantiate opponent session User and GameBoard
        session['opponent'] = new User(username: 'Game Opponent')
        session['opponentBoard'] = new GameBoard()

        session['opponentActions'] = new OpponentActions(
                opponent: session['opponentBoard'] as GameBoard,
                player: session['playerBoard'] as GameBoard,
                difficulty: OpponentActions.Difficulty.medium   //TODO eventually user-selected
        )


        //randomly pick first turn
        session['playerTurn'] = new Random().nextInt(2) == 1


        //timeout to delay instant opponent turn
        session['timeout'] = 3000

        println 'ATTEMPTING REDIRECT TO GameController gameOrchestrator'
        redirect(controller: 'Game', action: "gameOrchestrator")
    }
}
