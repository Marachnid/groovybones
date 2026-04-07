package game

import groovybones.game.GameBoard

/**
 * Game controller responsible for rendering game and generate random dice values
 * session dice are fixed values after render by routing through turnManager
 */
class GameController {

    /**
     * default method to render game.gsp
     * @return render game
     */
    def game() { log.info('GameController game()') }

    /**
     * separates number generation from default game rendering to prevent new numbers each browser refresh
     * @return render game
     */
    def turnManager() {
        log.info('GameController turnManager()')
        session['dice'] = new GameBoard().generateRandomDice()
        session['turn'] = (session['turn'] as int) + 1
        session['timeout'] = (new Random().nextInt(3) + 1) * 1250


        log.info("dice: ${session['dice']}")
        log.info("turn: ${session['turn']}")

        //redirect prevents turnManager from being reinstantiated and dice regenerating on browser refresh
        redirect(controller: 'game', action: 'game')
    }

}