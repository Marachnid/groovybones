package home

import groovybones.GameBoard
import groovybones.OpponentActions
import groovybones.RequestCaller
import opponent.Opponent
import user.User

/**
 * Default homepage controller that renders index view
 * views/home/index.gsp is implicitly rendered
 */
class GameSetupController {

    final String key = grailsApplication.config.apiKey.secretkey
    final String opponentAPI = 'http://localhost:8080/opponent'


    /**
     * single-line method to print a test message and render gameSetup
     * @return render gameSetup
     */
    def gameSetup() {
        println 'GameSetup gameSetup()'

        //retrieve opponent profiles from webservice/OpponentServiceController
        RequestCaller request = new RequestCaller(apiAuthKey: key)
        ArrayList<Map> opponentsMap = request.returnOpponents(request.callGET(opponentAPI))
        ArrayList<Opponent>collectedOpponents = []

        //collect opponents from API call, limit the list to 3 for now
        opponentsMap.eachWithIndex {it, i ->
            i++
            (i <= 3) ? collectedOpponents << (it.opponent as Opponent) : null
        }
        session['opponentsList'] = collectedOpponents

        render(view: 'gameSetup')
    }

    /**
     * initializes session variables to setup player and opponent game boards
     * activates gameOrchestrator for player vs. opponent turn sequencing
     * @return gameOrchestrator()
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
        op.printOpponent()


        //instantiate GameBoards to session
        session['playerBoard'] = new GameBoard()
        session['opponentBoard'] = new GameBoard()

        //instantiate opponent to session
        session['opponent'] = op

        //instantiate OpponentActions to session
        session['opponentActions'] = new OpponentActions(
                opponent: session['opponentBoard'] as GameBoard,
                player: session['playerBoard'] as GameBoard,
                difficulty: op.difficulty
        )


        session['playerTurn'] = new Random().nextInt(2) == 1      //randomly pick first turn
        session['timeout'] = 3000                                       //timeout to delay instant opponent turn
        redirect(controller: 'Game', action: "gameOrchestrator")
    }
}