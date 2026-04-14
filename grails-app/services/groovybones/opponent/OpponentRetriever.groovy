package groovybones.opponent


import groovybones.util.RequestCaller
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Responsible for mapping Opponents from OpponentServiceController responses
 * Execute requests on class instantiation via concrete constructors
 */
class OpponentRetriever {
    private static final Logger log = LoggerFactory.getLogger(OpponentRetriever)
    int responseCode


    /**
     * retrieves an opponent's stats
     * @param authKey api key
     * @param id opponent to find stats for
     * @return map of wins/losses/totalScore
     */
    Map retrieveOpponentStats(String domain, String authKey, Long id) {
        log.info('OpponentRetriever retrieveOpponentStats()')

        String newPath = "$domain/$id"
        RequestCaller requestCaller = new RequestCaller(apiAuthKey: authKey, path: newPath)

        Map response = requestCaller.callGET()
        responseCode = response.responseCode
        log.info("Response code: $responseCode")

        if (response.responseCode != 200) return null
        Map body = response.body as Map

        [wins: body?.wins, losses: body?.losses, totalScore: body?.totalScore]
    }


    /**
     * retrieves light list of opponents for display
     * @param authKey api key
     * @return list of all opponents as light values
     */
    ArrayList<Map> retrieveList(String domain, String authKey) {
        log.info('OpponentRetriever retrieveList()')

        RequestCaller requestCaller = new RequestCaller(apiAuthKey: authKey, path: domain)

        log.info('Making Request')

        Map response = requestCaller.callGET()
        responseCode = response.responseCode
        log.info("Response code: $responseCode")

        if (response.responseCode != 200) return null
        ArrayList<Map> body = response.body as ArrayList<Map>
        ArrayList<Map> opponents = []

        body.each {opponents << [id: it.id, username: it.username, difficulty: it.difficulty]}
        opponents
    }


    /**
     * sends update to webservice
     * @param authKey api key
     * @param id opponent to update
     * @param newValues to update
     */
    void postUpdate(String domain, String authKey, Long id, Map newValues) {
        log.info("OpponentRetriever postUpdate() for ID: $id")

        newValues.id = id

        RequestCaller requestCaller = new RequestCaller(apiAuthKey: authKey, path: domain, body: newValues)
        Map response = requestCaller.callPOST()
        responseCode = response.responseCode
        log.info("Response code: $responseCode")

        if (response.responseCode != 201) log.info("Update failed for ID: $id")
        else log.info('Update successful')
    }
}