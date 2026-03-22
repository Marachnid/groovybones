package groovybones

import groovy.json.JsonSlurper
import groovy.json.JsonOutput

/**
 * Responsible for building, executing, and returning parsed HTTP responses
 * returns responses as a Map with response code and body
 * includes simple auth secret in requests
 */
class RequestCaller {

    //simple auth secret
    def grailsApplication
    final String apiAuthKey = grailsApplication.config.apiKey.secretkey

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
}
