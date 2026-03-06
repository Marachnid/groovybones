package home

/**
 * Default homepage controller that renders index view
 * views/home/index.gsp is implicitly rendered
 */
class AboutController {

    /**
     * single-line method to print a test message and render index
     * @return render about.gsp
     */
    def about() { println 'AboutController about()' }
}
