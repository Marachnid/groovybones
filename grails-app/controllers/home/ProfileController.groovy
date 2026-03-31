package home


/**
 * Responsible for rendering Profile page
 */
class ProfileController {

    /**
     * default method to return profile page
     * @return render profile.gsp
     */
    def profile() {
        println 'ProfileController Profile()'

        if (session['player']) render(view: 'profile')
        else redirect(controller: 'home', action: 'index')
    }
}