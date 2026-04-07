package savedGame

import groovy.json.JsonSlurper
import groovybones.SavedGame
import groovybones.game.GameBoard
import groovybones.game.OpponentActions
import groovybones.opponent.OpponentRetriever
import groovybones.savedGame.SavedGameService
import groovybones.user.UserService


/**
 * handles routing SavedGame operations used in various places
 * load/save/delete
 */
class SavedGameController {
    final String key = grailsApplication.config.getProperty('apiKey.secretkey', String)

    /**
     * saves the current game instance and redirects back to home
     * cleans up game-related session variables
     * @return redirect to home
     */
    def saveExit() {
        log.info("userBoard: ${session['userBoard'].board.toString()}")
        log.info("opponentBoard: ${session['opponentBoard'].board.toString()}")

        Map savedGame = [
                userId: session['userId'],
                opponentId: session['opponentId'],
                userBoard: session['userBoard'].board,
                opponentBoard: session['opponentBoard'].board,
                turn: session['turn']
        ]

        new SavedGameService().addSavedGame(savedGame)

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

        redirect(controller: 'home', action: 'index')
    }


    /**
     * deletes selected SavedGame by ID, redirect to gameSetup
     * @return redirect to gameSetup
     */
    def deleteSave() {
        new SavedGameService().deleteSavedGame(params.id as Long)
        redirect(controller: 'gameSetup', action: 'gameSetup')
    }


    /**
     * Re-initializes a SavedGame into an active in-session game
     * must de-parse userBoard and opponentBoard into ArrayLists
     * @return
     */
    def loadSave() {
        ArrayList<Map> savedGames = session['savedGames'] as ArrayList<Map>
        SavedGame savedGame = savedGames.find {it.id = params.id} as SavedGame

        ArrayList parsedUserBoard
        ArrayList parsedOpponentBoard

        if (!savedGame.userBoard) savedGame.userBoard = [[],[],[]]
        else parsedUserBoard = new JsonSlurper().parseText(savedGame.userBoard) as ArrayList

        if (!savedGame.opponentBoard) savedGame.opponentBoard = [[],[],[]]
        else parsedOpponentBoard = new JsonSlurper().parseText(savedGame.opponentBoard) as ArrayList


        session['userBoard'] = new GameBoard().tap {it.board = parsedUserBoard}
        session['opponentBoard'] = new GameBoard().tap {it.board = parsedOpponentBoard}
        session['opponentId'] = savedGame.opponentId
        session['turn'] = savedGame.turn

        Map opDetails = session['opponentList'].find {it.id == savedGame.opponentId} as Map
        session['opponentUsername'] = opDetails.username


        session['opponentStats'] = new OpponentRetriever().retrieveOpponentStats(key, savedGame.opponentId)
        session['userStats'] = new UserService().getUserStats(session['userId'] as Long)


        //configure Game Instance for orchestrating opponent actions
        session['opponentActions'] = new OpponentActions(
                opDetails.difficulty as int,
                session['opponentBoard'] as GameBoard,
                session['userBoard'] as GameBoard
        )

        session['userTurn'] = true                                     //opponent will most likely move before save
        session['timeout'] = 3000                                     //timeout to delay instant opponent turn

        //remove old saved game
        new SavedGameService().deleteSavedGame(params.id as Long)
        redirect(controller: 'Game', action: "turnManager")
    }
}