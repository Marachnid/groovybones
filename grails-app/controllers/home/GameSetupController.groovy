package home

import groovybones.GameBoard
import groovybones.OpponentActions
import groovybones.OpponentRetriever
import opponent.Opponent

/**
 * Responsible for initializing and instantiating a new game
 * Loads opponent profiles via OpponentServiceController (webservice) HTTP requests
 * Instantiates player and opponent session variables for gameplay
 */
class GameSetupController {
    final String key = grailsApplication.config.apiKey.secretkey
    final String opponentAPI = 'http://localhost:8080/opponent'


    /**
     * renders gameSetup
     * makes request against OpponentServiceController to gather opponent entities
     * @return render gameSetup
     */
    def gameSetup() {
        println 'GameSetup gameSetup()'

        OpponentRetriever opRet = new OpponentRetriever(key, opponentAPI, false)
        ArrayList<Opponent> opponents = opRet.opponent
        session['opponentsList'] = opponents

        render(view: 'gameSetup')
    }

    /**
     * initializes session variables to setup player and opponent game boards
     * retrieves opponent details from gameSetup opponent form
     * activates gameOrchestrator for player vs. opponent turn sequencing
     * @return GameController gameOrchestrator() -> game
     */
    def gameInitialization() {
        println 'GameSetup gameInitialization()'

        //grab opponent from form params
        Opponent op = new Opponent(
                username: params.username,
                difficulty: params.difficulty,
                wins: params.wins,
                losse: params.losses,
                totalScore: params.totalscore
        )

        //instantiate GameBoards to session
        session['playerBoard'] = new GameBoard()
        session['opponentBoard'] = new GameBoard()

        //instantiate opponent to session
        session['opponent'] = op


        //instantiate OpponentActions to session
        session['opponentActions'] = new OpponentActions(
                op.difficulty,
                session['opponentBoard'] as GameBoard,
                session['playerBoard'] as GameBoard
        )


        session['turn'] = 0                                            //initialize turn counter (visual only)
        session['playerTurn'] = new Random().nextInt(2) == 1    //randomly pick first turn
        session['timeout'] = 3000                                     //timeout to delay instant opponent turn
        redirect(controller: 'Game', action: "gameOrchestrator")
    }
}