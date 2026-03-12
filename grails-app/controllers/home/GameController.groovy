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
     * default method to render game.gsp and generate a random dice value for subsequent turns
     * @return render game.gsp
     */
    def game() {
        println 'GameController game()'
        session['dice'] = new GameBoard().generateNumber()
        render(view: 'game')
    }


    /**
     * initializes session variables to setup player and opponent game boards
     * @return render game.gsp
     */
    def gameInitialization() {
        println 'GameController gameInitialization()'

        //create player GameBoard object
        session['playerBoard'] = new GameBoard(boardName: session['player'].userName)

        //initialize opponent and opponent GameBoard object
        User opponent = new User(userName: 'Game Opponent')
        session['opponent'] = opponent
        session['opponentBoard'] = new GameBoard(boardName: opponent.userName)

        //generate a random dice value
        session['dice'] = new GameBoard().generateNumber()
        session['playerTurn'] = true
        render(view: 'game')
    }
}