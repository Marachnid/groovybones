package home


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
        log.info('HomeController index()')

        //TODO need to add some global controller or method to handle clearing game session variables
        //TODO need to handle unintended navigations to game/gameOver
        session['opponent'] = null
        session['playerBoard'] = null
        session['opponentBoard'] = null
        session['playerScore'] = null
        session['opponentScore'] = null

        render(view: 'index')
    }
}