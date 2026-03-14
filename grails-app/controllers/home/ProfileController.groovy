package home


/**
 * profile router
 */
class ProfileController {

    /**
     * single-line method to print a test message and render index
     * @return render profile.gsp
     */
    def profile() {
        println 'ProfileController Profile()'

        if (session['player']) render(view: 'profile')
        else redirect(controller: 'home', action: 'index')
    }
}