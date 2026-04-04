package game

import groovybones.game.GameBoard
import groovybones.game.OpponentActions
import groovybones.opponent.OpponentRetriever
import groovybones.Opponent

/**
 * Responsible for initializing and instantiating a new game
 * Loads opponent profiles via OpponentServiceController (webservice) HTTP requests
 * Instantiates user and opponent session variables for gameplay
 */
class GameSetupController {
    final String key = grailsApplication.config.getProperty('apiKey.secretkey', String)
    final String opponentAPI = 'http://localhost:8080/opponent'


    /**
     * renders gameSetup
     * makes request against OpponentServiceController to gather opponent entities
     * @return render gameSetup
     */
    def gameSetup() {
        log.info('GameSetup gameSetup()')

        final OpponentRetriever opRet = new OpponentRetriever(key, opponentAPI, false)
        session['opponentsList'] = opRet.opponent

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

        //grab opponent from form params
        final Opponent opponent = new Opponent(
                username: params.username,
                difficulty: params.difficulty,
                wins: params.wins,
                losse: params.losses,
                totalScore: params.totalscore
        )

        log.info("Opponent: ${params.username}, difficulty: ${params.difficulty}, wins: ${params.wins}, " +
                "losses: ${params.losses}, totalScore: ${params.totalScore} selected")


        //instantiate GameBoards to session
        session['userBoard'] = new GameBoard()
        session['opponentBoard'] = new GameBoard()

        //instantiate opponent to session
        session['opponent'] = opponent

        //instantiate OpponentActions to session
        session['opponentActions'] = new OpponentActions(
                opponent.difficulty,
                session['opponentBoard'] as GameBoard,
                session['userBoard'] as GameBoard
        )


        session['turn'] = 0                                            //initialize turn counter (visual only)
        session['playerTurn'] = new Random().nextInt(2) == 1    //randomly pick first turn
        session['timeout'] = 3000                                     //timeout to delay instant opponent turn
        redirect(controller: 'Game', action: "turnManager")
    }
}