package home

import groovybones.GameBoard
import groovybones.Opponent
import groovybones.User
import groovybones.opponent.OpponentRetriever
import groovybones.opponent.OpponentService
import groovybones.user.UserService

/**
 * Responsible for handling post-game operations
 */
class GameOverController {

    final String key = grailsApplication.config.getProperty('apiKey.secretkey', String)
    final String opponentAPI = 'http://localhost:8080/opponent'

    /**
     * default method to render gameOver page
     * @return render gameOver
     */
    def gameOver() { log.info('GameOverController gameOver()') }


    def gameOverAction() {
        log.info('GameOver gameOverAction()')

        User user = session['player'] as User
        Opponent opponent = session['opponent'] as Opponent
        GameBoard playerGB
        GameBoard opGB


        //TODO placeholder for testing
        if (!session['turn']) {
            opponent = new OpponentRetriever(key, opponentAPI + '/1', true).opponent
            playerGB = new GameBoard()
            opGB = new GameBoard()
            playerGB.board = [[2,1],[1,1],[3,4]]
            opGB.board = [[2,2],[3,5],[2,1,1]]
            session['playerBoard'] = playerGB
            session['opponentBoard'] = opGB

        } else {
            //TODO keep
            playerGB = session['playerBoard'] as GameBoard
            opGB = session['opponentBoard'] as GameBoard
        }

        final int playerScore = playerGB.calculateScore()
        final int opScore = opGB.calculateScore()

        user.totalScore += playerScore
        opponent.totalScore += opScore
        log.info("player-totalScore: ${user.totalScore}, opponent-totalScore: ${opponent.totalScore}")


        //determine win/loss/tie
        if (playerScore > opScore) {
            log.info("Player won ($playerScore), Opponent lost ($opScore")
            user.wins ++
            opponent.losses ++

        } else if (playerScore < opScore) {
            log.info("Opponent won ($opScore), Player lost ($playerScore")
            opponent.wins ++
            user.losses ++

        } else log.info("Player and Opponent tied: p:$playerScore, o:$opScore")


        //update user - log responses
        if (new UserService().updateUser(user))
            log.info("Update for User ID: ${user.id} successful")
        else
            log.info("Update for User ID: ${user.id} failed")

        //update opponent - log responses
        if (new OpponentRetriever(key, opponentAPI, opponent).responseCode == 201)
            log.info("Update for Opponent ID: ${opponent.id} successful")
        else
            log.info("Update for Opponent ID: ${opponent.id} failed")


        redirect(controller: 'GameOver', action: 'gameOver')
    }
}
