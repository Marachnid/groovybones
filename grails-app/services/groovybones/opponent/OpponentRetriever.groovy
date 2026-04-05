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
    private final String path = 'http://localhost:8080/opponent'

    int responseCode


    /**
     * retrieves an opponent's stats
     * @param authKey api key
     * @param id opponent to find stats for
     * @return map of wins/losses/totalScore
     */
    Map retrieveOpponentStats(String authKey, Long id) {
        log.info('OpponentRetriever retrieveOpponentStats()')

        String newPath = "$path/$id"
        RequestCaller requestCaller = new RequestCaller(apiAuthKey: authKey, path: newPath)

        Map response = requestCaller.callGET()
        log.info("Response code: ${response.responseCode}")

        if (response.responseCode != 200) return null
        Map body = response.body as Map

        [wins: body?.wins, losses: body?.losses, totalScore: body?.totalScore]
    }


    /**
     * retrieves light list of opponents for display
     * @param authKey api key
     * @return list of all opponents as light values
     */
    ArrayList<Map> retrieveList(String authKey) {
        log.info('OpponentRetriever retrieveList()')

        RequestCaller requestCaller = new RequestCaller(apiAuthKey: authKey, path: path)

        Map response = requestCaller.callGET()
        log.info("Response code: ${response.responseCode}")

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
    void postUpdate(String authKey, Long id, Map newValues) {
        log.info("OpponentRetriever postUpdate() for ID: $id")

        newValues.id = id

        RequestCaller requestCaller = new RequestCaller(apiAuthKey: authKey, path: path, body: newValues)
        Map response = requestCaller.callPOST()
        log.info("Response code: ${response.responseCode}")

        if (response.responseCode != 201) log.info("Update failed for ID: $id")
        else log.info('Update successful')
    }
}