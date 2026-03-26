package groovybones.opponent


import groovybones.Opponent

/**
 * Responsible for mapping Opponents from OpponentServiceController responses
 * Execute requests on class instantiation via concrete constructors
 */
class OpponentRetriever {

    //request defaults
    RequestCaller request
    String apiAuthKey
    String path
    Map postBody

    //response defaults
    Map response
    Map responseBody
    int responseCode

    //resulting opponent(s)
    def opponent


    /** default constructor */
    OpponentRetriever() {}

    /**
     * loaded constructor to execute and parse GET requests to OpponentServiceController webservice
     * @param apiAuthKey webservice auth key (easier to pass config props from controllers)
     * @param path path to execute
     * @param singleOp TRUE for single item requests, FALSE for list requests
     */
    OpponentRetriever(String apiAuthKey, String path, Boolean singleOp) {
        this.apiAuthKey = apiAuthKey
        this.path = path

        //perform request on instantiation
        request = new RequestCaller(apiAuthKey: apiAuthKey, path: path)
        response = request.callGET()
        responseCode = response.responseCode
        responseBody = response.get('body') as Map
        (singleOp) ? (opponent = returnOpponent()) : (opponent = returnOpponents())
    }

    /**
     * loaded constructor to execute and parse POST requests to OpponentServiceController webservice
     * @param apiAuthKey webservice auth key (easier to pass config props from controllers)
     * @param path path to execute
     * @param postBody
     */
    OpponentRetriever(String apiAuthKey, String path, Opponent op) {
        this.apiAuthKey = apiAuthKey
        this.path = path
        this.postBody = op.returnAsMap()

        request = new RequestCaller(apiAuthKey: apiAuthKey, path: path, body: postBody)
        response = request.callPOST()
        responseCode = response.responseCode
        responseBody = response.get('body') as Map
        opponent = returnOpponent()
    }


    /**
     * maps and returns a single Opponent from response body
     * @return new Opponent if responseCode == 200, else null Opponent
     */
    Opponent returnOpponent() {
        Opponent opponent
        if (responseCode == 200 || responseCode == 201) {
            opponent = new Opponent(responseBody)
            opponent.id = responseBody.id as Long
        } else opponent = null
        opponent
    }

    /**
     * maps and returns list of opponents from response body
     * @return list of opponents if responseCode == 200, else empty list
     */
    ArrayList<Opponent> returnOpponents() {
        ArrayList<Opponent> collectedOpponents = []
        if (responseCode == 200) {
            ArrayList<Map> opponents = responseBody as ArrayList<Map>
            opponents.each {
                opponent = new Opponent(it)
                opponent.id = it.id as Long
                collectedOpponents << opponent
            }
        }
        collectedOpponents
    }
}