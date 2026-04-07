package game

import groovybones.game.GameBoard
import groovybones.opponent.OpponentRetriever
import groovybones.user.UserService

/**
 * Responsible for handling post-game operations
 */
class GameOverController {
    final String key = grailsApplication.config.getProperty('apiKey.secretkey', String)

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

        final GameBoard userBoard = session['userBoard'] as GameBoard
        final GameBoard opponentBoard = session['opponentBoard'] as GameBoard
        final int userScore = userBoard.calculateScore()
        final int opScore = opponentBoard.calculateScore()

        final Map userStats = session['userStats'] as Map
        final Map opStats = session['opponentStats'] as Map
        final boolean userWon = (userScore >= opScore)        //ties go to the user

        session['userWon'] = userWon
        session['userScore'] = userScore
        session['opponentScore'] = opScore

        userStats.totalScore += userScore
        opStats.totalScore += opScore

        if (userWon) {
            log.info("User won ($userScore), Opponent lost ($opScore")
            userStats.wins ++
            opStats.losses ++

        } else {
            log.info("Opponent won ($opScore), User lost ($userScore")
            opStats.wins ++
            userStats.losses ++
        }

        //clean up old game session variables
        session['playerStats'] = null
        session['opponentStats'] = null
        session['opponentActions'] = null
        session['userBoard'] = null
        session['opponentBoard'] = null
        session['turn'] = null
        session['dice'] = null
        session['userTurn'] = null
        session['timeout'] = null


        log.info("User stats: ${userStats.toString()}")
        log.info("Opponent stats: ${opStats.toString()}")

        //update user and opponent
        new UserService().updateUser(session['userId'] as Long, userStats)
        new OpponentRetriever().postUpdate(key, session['opponentId'] as Long, opStats)

        redirect(controller: 'GameOver', action: 'gameOver')
    }
}