package webservice

import opponent.Opponent


/**
 * Opponent WEBSERVICE (paths are defined in URLMappings --- localhost:8080/opponent)
 */
class OpponentServiceController {

    //used by respond, returns responses as JSON
    static responseFormats = ['json']


    /**
     * get all method - returns all opponents as list
     * localhost:8080/opponent
     * @return list of opponent entities
     */
    def get() {
        respond Opponent.list()
    }

    /**
     * get by ID method
     * @param id ID query parameter
     * @return found opponent
     */
    def getById(int id) {
        respond Opponent.findById(id)
    }


    /**
     * POST method to create OR update existing opponent entities
     * localhost:8080/opponent
     * @return opponent entity saved
     */
    def post() {
        Opponent opponent = new Opponent(request.JSON as Map)
        opponent.save()

        respond opponent, status: 201
    }
}
