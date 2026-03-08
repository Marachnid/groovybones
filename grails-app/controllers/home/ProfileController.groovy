package home

import user.User

/**
 * Default homepage controller that renders index view
 * views/home/index.gsp is implicitly rendered
 */
class ProfileController {

    /**
     * single-line method to print a test message and render index
     * @return render profile.gsp
     */
    def profile() {
        println 'ProfileController Profile()'

        if (session['mockID']) {
            println session['mockID']
            [user: User.get(session['mockID'])]
            render(view: 'profile')
        }
        else {
            redirect(controller: 'home', action: 'index')
        }
    }
}