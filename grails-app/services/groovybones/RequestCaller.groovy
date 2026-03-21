package groovybones

import groovy.json.JsonSlurper
import groovy.json.JsonOutput

/**
 * Responsible for building, executing, and returning parsed HTTP responses
 * returns responses as a Map with response code and body
 */
class RequestCaller {

    /**
     * build/execute/parse GET request
     * @param path path to be called
     * @return response map
     */
    static Map callGET(String path) {
        URL url = new URL(path)
        HttpURLConnection conn = (HttpURLConnection) url.openConnection()

        conn.setRequestMethod("GET")
        conn.doOutput = true
        conn.setRequestProperty("Content-Type", "application/json")

        //execute request and parse response
        def response = conn.inputStream.text
        def jsonResponse = new JsonSlurper().parseText(response)

        //return response as map with response code
        ['responseCode': conn.responseCode, 'body': jsonResponse]
    }


    /**
     * build/execute/parse POST request
     * @param path path to be called
     * @param body request body
     * @return POST results with response code and updated body
     */
    static Map callPOST(String path, Map body) {
        URL url = new URL(path)
        def jsonBody = JsonOutput.toJson(body)
        HttpURLConnection conn = (HttpURLConnection) url.openConnection()

        conn.setRequestMethod("POST")
        conn.doOutput = true
        conn.setRequestProperty("Content-Type", "application/json")
        conn.outputStream.withWriter { it << jsonBody }

        //execute request
        def response = conn.inputStream.text
        def jsonResponse = new JsonSlurper().parseText(response)

        //return response as map with response code
        ['responseCode': conn.responseCode, 'body': jsonResponse]
    }
}
