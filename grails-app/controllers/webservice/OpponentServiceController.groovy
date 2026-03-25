package webservice

import grails.gorm.transactions.Transactional
import groovybones.opponent.OpponentService
import opponent.Opponent

/**
 * Opponent WEBSERVICE (paths are defined in URLMappings --- localhost:8080/opponent)
 * validates incoming requests with basic secret key
 * includes error messaging for various validation fails
 */
class OpponentServiceController {
    static final responseFormats = ['json']
    final String key = grailsApplication.config.apiKey.secretkey
    final Map forbiddenError = [errorText: 'Forbidden']
    OpponentService opService
    String requestKey
    Opponent opponent


    /**
     * checks request auth key against stored auth key
     * @param requestKey incoming key
     * @return true if keys match, else false
     */
    boolean validateAuthKey(String requestKey) { (requestKey == key) }


    /**
     * get all method - returns all opponents as a list
     * localhost:8080/opponent
     * @return list of opponent entities
     */
    def get() {
        requestKey = request.getHeader('X-API-KEY')
        if (!validateAuthKey(requestKey)) respond(forbiddenError, status: 403)
        else respond Opponent.list(), status: 200
    }

    /**
     * get by ID method
     * localhost://8080/opponent/{id}
     * @param id ID query parameter - Long id defaults invalid parameters to null/false
     * @return found opponent
     */
    def getById(Long id) {
        requestKey = request.getHeader('X-API-KEY')
        if (!validateAuthKey(requestKey)) respond(forbiddenError, status: 403)

        //if invalid id, respond with 400, else query opponent id
        if (!id) respond([errorText: 'Invalid ID'], status: 400)
        else opponent = Opponent.get(id)

        //if opponent is not found, respond with 404, else respond with 200
        if (!opponent) respond([errorText: "ID($id) not found"], status: 404)
        else respond opponent, status: 200
    }

    /**
     * POST method to create OR update existing opponent entities
     * localhost:8080/opponent + body
     * @return opponent entity saved
     */
    @Transactional
    def post() {
        requestKey = request.getHeader('X-API-KEY')
        if (!validateAuthKey(requestKey)) respond(forbiddenError, status: 403)

        final json = request.JSON

        //if missing a JSON body, respond with 400, else assign opponent to JSON body
        if (!json) respond([errorText: 'Bad Request'], status: 400)
        else opponent = new Opponent(json as Map)


        //if invalid entry, respond with 500
        if (!opponent.validate()) respond([errorText: opponent.errors], status: 500)
        else
            opService = new OpponentService()
            opponent = opService.updateOpponent(opponent)
            respond opponent, status: 201
    }
}