package groovybones

import groovy.json.JsonSlurper
import groovy.json.JsonOutput
import opponent.Opponent

/**
 * Responsible for building, executing, and returning parsed HTTP responses
 * returns responses as a Map with response code and body
 * includes simple auth secret in requests
 */
class RequestCaller {

    //easier to pass key in from controllers (classpath/scope issues pulling application.yml secrets in services/)
    String apiAuthKey

    /**
     * reusable response parser
     * parses JSON body if response code >= 200 && < 300
     * @param conn open HTTP connection
     * @return response output map
     */
    static Map parseOutput(HttpURLConnection conn, URL url) {
        if (conn.responseCode >= 200 && conn.responseCode < 300) {
            def response = conn.inputStream.text
            def jsonResponse = new JsonSlurper().parseText(response)
            return [responseCode: conn.responseCode, body: jsonResponse]

        } else {
            return [responseCode: conn.responseCode, message: conn.errorStream.text, url: url]
        }
    }

    /**
     * convenience method to return opponent ID and Opponent object mapped from parseOutput()
     * Opponent domain class doesn't modify IDs, keeping them separate to prevent unintended operations
     * returns a default opponent if not receiving a 200 code
     * @param response mapped output from parseOutput() (get/post requests)
     * @return Map of opponent ID and Opponent object
     */
    static Map returnOpponent(Map response) {
        Opponent opponent
        int id

        //assign opponent if valid, else assign default opponent
        if (response.responseCode == 200) {
            Map body = response.get('body') as Map

            id = body.id as int
            opponent = new Opponent(
                    username: body.username,
                    difficulty: body.difficulty as int,
                    wins: body.wins as int,
                    losses: body.losses as int,
                    totalScore: body.totalScore as int
            )
        } else {
            //not sure how webflow will react to POST returning a 400 for this yet
            id = 0
            opponent = new Opponent(username: 'Default', difficulty: 2, wins: 0, losses: 0, totalScore: 0)
        }

        [id: id, opponent: opponent]
    }

    /**
     * convenience method to return list of opponent IDs and Opponent objects mapped from parseOutput()
     * Opponent domain class doesn't modify IDs, keeping them separate to prevent unintended operations
     * returns default opponents if not receiving a 200 code
     * @param response mapped output from parseOutput() (get/post requests)
     * @return ArrayList<Map> of opponent ID and Opponent object
     */
    static ArrayList<Map> returnOpponents(Map response) {
        ArrayList<Map> collectedOpponents = [:]
        Opponent opponent
        def id

        //assign opponents if valid, else assign default opponents
        if (response.responseCode == 200) {
            ArrayList<Map> opponents = response.body as ArrayList<Map>

            opponents.each {
                id = it.id as int
                opponent = new Opponent(
                        username: it.username,
                        difficulty: it.difficulty as int,
                        wins: it.wins as int,
                        losses: it.losses as int,
                        totalScore: it.totalScore as int
                )
                collectedOpponents << [id: id, opponent: opponent]
            }
        } else {
            int i = 1
            3.times {
                //not sure how webflow will react to POST returning a 400 for this yet
                id = 0
                opponent = new Opponent(username: "Default-$i", difficulty: i, wins: 0, losses: 0, totalScore: 0)
                collectedOpponents << [id: id, opponent: opponent]
                i++
            }
        }

        collectedOpponents
    }

    /**
     * build/execute/parse GET request
     * @param path path to be called
     * @return response output map
     */
    Map callGET(String path) {
        URL url = new URL(path)
        HttpURLConnection conn = (HttpURLConnection) url.openConnection()

        conn.setRequestMethod('GET')
        conn.doOutput = true
        conn.setRequestProperty('Content-Type', 'application/json')
        conn.setRequestProperty('X-API-KEY', apiAuthKey)

        parseOutput(conn, url)
    }

    /**
     * build/execute/parse POST request
     * @param path path to be called
     * @param body request body
     * @return POST results with response code and updated body
     */
    Map callPOST(String path, Map body) {
        URL url = new URL(path)
        def jsonBody = JsonOutput.toJson(body)
        HttpURLConnection conn = (HttpURLConnection) url.openConnection()

        conn.setRequestMethod('POST')
        conn.doOutput = true
        conn.setRequestProperty('Content-Type', 'application/json')
        conn.setRequestProperty('X-API-KEY', apiAuthKey)
        conn.outputStream.withWriter { it << jsonBody }

        parseOutput(conn, url)
    }
}