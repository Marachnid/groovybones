package game

import groovybones.game.GameBoard
import groovybones.game.OpponentActions
import groovybones.opponent.OpponentRetriever
import groovybones.Opponent
import groovybones.user.UserService

/**
 * Responsible for initializing and instantiating a new game
 * Loads opponent profiles via OpponentServiceController (webservice) HTTP requests
 * Instantiates user and opponent session variables for gameplay
 */
class GameSetupController {
    final String key = grailsApplication.config.getProperty('apiKey.secretkey', String)


    /**
     * renders gameSetup
     * makes request against OpponentServiceController to gather opponent entities
     * @return render gameSetup
     */
    def gameSetup() {
        log.info('GameSetup gameSetup()')

        session['opponentsList'] = new OpponentRetriever().retrieveList(key)
        session['savedGames'] = new UserService().getUserSavedGames(session['userId'] as Long)

        render(view: 'gameSetup')
    }

    /**
     * initializes session variables to setup user and opponent game boards
     * retrieves opponent details from gameSetup opponent form
     * activates turnManager for user vs. opponent turn sequencing
     * @return GameController turnManager() -> game
     */
    def gameInitialization() {
        log.info('GameSetup gameInitialization()')


        session['opponentId'] = params.id
        session['opponentUsername'] = params.username
        session['opponentBoard'] = new GameBoard()
        session['opponentStats'] = new OpponentRetriever().retrieveOpponentStats(key, params.id as Long)

        session['userStats'] = new UserService().getUserStats(session['userId'] as Long)
        session['userBoard'] = new GameBoard()


        //configure Game Instance for orchestrating opponent actions
        session['opponentActions'] = new OpponentActions(
                params.difficulty as int,
                session['opponentBoard'] as GameBoard,
                session['userBoard'] as GameBoard
        )


        session['turn'] = 0                                            //initialize turn counter (visual only)
        session['userTurn'] = new Random().nextInt(2) == 1      //randomly pick first turn
        session['timeout'] = 3000                                     //timeout to delay instant opponent turn
        redirect(controller: 'Game', action: "turnManager")
    }
}