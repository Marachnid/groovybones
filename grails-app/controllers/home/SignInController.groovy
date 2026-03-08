package home

import groovybones.GameBoard
import user.User

/**
 * Default homepage controller that renders index view
 * views/home/index.gsp is implicitly rendered
 */
class SignInController {

    /**
     *
     */
    def signIn() { println 'SignInController signIn()' }



    //only a POC for building user flow
    def doSignIn() {
        String username = params.username?.trim()

        if (!username) redirect(action: "signIn")
        else if (User.findByUserName(username) == null) redirect(action: "signIn")
        else {
            User player = session['player'] = User.findByUserName(username)

            //TODO temp initializations
            session['playerBoard'] = new GameBoard(boardName: player.userName)

            User opponent = new User(userName: 'Game Opponent')
            session['opponent'] = opponent
            session['opponentBoard'] = new GameBoard(boardName: opponent.userName)
            redirect(controller: "home", action: "index")
        }
    }


    def signOut() {
        session.invalidate()
        redirect(controller: "home", action: "index")
    }
}
