package home

/**
 * Default homepage controller that renders index view
 * views/home/index.gsp is implicitly rendered
 */
class SignInController {

    /**
     * single-line method to print a test message and render index
     * @return render index.gsp
     */
    def signin() {
        println 'ProfileController Profile()'
    }
}
