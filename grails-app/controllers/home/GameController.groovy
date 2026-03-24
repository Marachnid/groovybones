package home
import groovybones.GameBoard


/**
 * Game controller responsible for rendering game and generate random dice values
 * session dice are fixed values after render by routing through gameOrchestrator
 */
class GameController {

    /**
     * default method to render game.gsp
     * @return render game
     */
    def game() { println 'GameController game()' }

    /**
     * separates number generation from default game rendering to prevent new numbers each browser refresh
     * @return render game
     */
    def gameOrchestrator() {
        println 'gameAction gameOrchestrator()'
        session['dice'] = new GameBoard().generateRandomDice()
        session['turn'] = (session['turn'] as int) + 1

        //redirect prevents gameOrchestrator from being reinstantiated and dice regenerating on browser refresh
        redirect(controller: 'game', action: 'game')
    }
}