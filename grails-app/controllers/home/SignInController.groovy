package home
import user.User

/**
 * Handles rendering signin.gsp and signing a user in and out
 */
class SignInController {


    /**
     * default render method for signin.gsp
     * @return render signin.gsp
     */
    def signIn() { println 'SignInController signIn()' }



    //only a POC for building user flow
    /**
     * signs a user in
     * @return renders home on successful signin, otherwise re-renders signin.gsp
     */
    def doSignIn() {
        String username = params.username?.trim()

        if (!username)
            redirect(action: "signIn")
        else if (User.findByUserName(username) == null)
            redirect(action: "signIn")
        else
            session['player'] = User.findByUserName(username)
            redirect(controller: "home", action: "index")
    }


    /**
     * signs a user out
     * @return render home page
     */
    def signOut() {
        session.invalidate()
        redirect(controller: "home", action: "index")
    }
}