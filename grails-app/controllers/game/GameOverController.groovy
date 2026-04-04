package game

import groovybones.game.GameBoard
import groovybones.Opponent
import groovybones.User
import groovybones.opponent.OpponentRetriever
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


    /**
     * Processes game-over updates to user and opponent persistence entities
     * cleans up old game session variables
     * @return
     */
    def gameOverAction() {
        log.info('GameOver gameOverAction()')

        final User user = session['user'] as User
        final GameBoard userBoard = session['userBoard'] as GameBoard
        final int userScore = userBoard.calculateScore()

        final Opponent opponent = session['opponent'] as Opponent
        final GameBoard opponentBoard = session['opponentBoard'] as GameBoard
        final int opScore = opponentBoard.calculateScore()
        final boolean userWon = (userScore >= opScore)        //ties go to the user

        user.totalScore += userScore
        opponent.totalScore += opScore
        log.info("userScore-totalScore: ${user.totalScore}, opponent-totalScore: ${opponent.totalScore}")

        session['userWon'] = userWon
        session['opponentName'] = opponent.username
        session['userScore'] = userScore
        session['opponentScore'] = opScore


        //clean up old game session variables
        session['opponentActions'] = null
        session['userBoard'] = null
        session['opponentBoard'] = null
        session['opponent'] = null
        session['turn'] = null
        session['dice'] = null
        session['userTurn'] = null


        //determine win/loss persistence
        if (userWon) {
            log.info("User won ($userScore), Opponent lost ($opScore")
            user.wins ++
            opponent.losses ++

        } else {
            log.info("Opponent won ($opScore), User lost ($userScore")
            opponent.wins ++
            user.losses ++
        }


        //update user - log responses
        if (new UserService().updateUser(user)) log.info("Update for User ID: ${user.id} successful")
        else log.info("Update for User ID: ${user.id} failed")

        //update opponent - log responses
        if (new OpponentRetriever(key, opponentAPI, opponent).responseCode == 201) log.info("Update for Opponent ID: ${opponent.id} successful")
        else log.info("Update for Opponent ID: ${opponent.id} failed")


        redirect(controller: 'GameOver', action: 'gameOver')
    }
}
