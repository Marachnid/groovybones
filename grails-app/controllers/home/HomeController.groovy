package home

import groovybones.GameBoard
import user.User

/**
 * Default homepage controller that renders index view
 * views/home/index.gsp is implicitly rendered
 */
class HomeController {

    /**
     * single-line method to print a test message and render index
     * @return render index.gsp
     */
    def index() {
        println 'HomeController index()'

        //TODO Temp initializations

        User player = User.get(1)
        session['player'] = player
        session['playerBoard'] = new GameBoard(boardName: player.userName)

        User opponent = new User(userName: 'Game Opponent')
        session['opponent'] = opponent
        session['opponentBoard'] = new GameBoard(boardName: opponent.userName)


        render(view: 'index')
    }
}