package home
import groovybones.GameBoard
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
     * @return initiate gameOrchestrator()
     */
    def gameInitialization() {
        println 'GameController gameInitialization()'

        //create player GameBoard object
        session['playerBoard'] = new GameBoard()

        //initialize opponent and opponent GameBoard object
        //TODO this will eventually include opponent behavior profiles (easy/medium/hard)
        User opponent = new User(username: 'Game Opponent')
        session['opponent'] = opponent
        session['opponentBoard'] = new GameBoard()


        //initiate to player turn first - eventually might make a 50/50 chance between opponent or player starting
        session['playerTurn'] = true

        //if !playerTurn, game.gsp scriptlet will auto execute GameActionController runOpponenBoard() after timeout
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