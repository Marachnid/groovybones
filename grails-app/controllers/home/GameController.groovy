package home
import groovybones.GameBoard
import groovybones.OpponentActions
import user.User

/**
 * Game controller responsible for rendering a player vs. opponent game
 * Initializes session variables to run game actions in GameActionController
 * @see GameActionController
 */
class GameController {

    /**
     * default method to render game.gsp
     * @return render game.gsp
     */
    def game() { println 'GameController game()' }


    /**
     * initializes session variables to setup player and opponent game boards
     * activates gameOrchestrator for player vs. opponent turn sequencing
     * @return gameOrchestrator()
     */
    def gameInitialization() {
        println 'GameController gameInitialization()'

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


        //initiate to player turn first
        session['playerTurn'] = true

        //timeout to delay instant opponent turn
        session['timeout'] = 3000
        gameOrchestrator()
    }


    /**
     * separates number generation from default game rendering to prevent new numbers each browser refresh
     * would control player vs. opponent sequencing/actions here
     * @return render game.gsp
     */
    def gameOrchestrator() {
        println 'gameAction gameOrchestrator()'
        session['dice'] = new GameBoard().generateRandomDice()

        //redirect prevents gameOrchestrator from being reinstantiated and dice regenerating on browser refresh
        redirect(controller: 'game', action: 'game')
    }
}