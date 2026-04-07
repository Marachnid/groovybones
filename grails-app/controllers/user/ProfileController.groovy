package user


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

        if (session['user']) render(view: 'profile')
        else redirect(controller: 'home', action: 'index')
    }
}