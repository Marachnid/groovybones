package opponent

import grails.core.GrailsApplication
import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovybones.Opponent
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise
import util.SQLRunner
import javax.sql.DataSource


/** Performs integration tests for webservice persistence methods and HTTP responses */
@Integration
@Rollback
@Stepwise
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OpponentServiceControllerIntegrationSpec extends Specification {
    @Shared
    DataSource dataSource

    @LocalServerPort
    int port

    GrailsApplication grailsApplication
    HttpURLConnection conn
    Opponent opponent
    String key
    String basePath
    def jsonResponse
    Map body
    URL url


    /** basic setup method to instantiate objects and refresh test schema*/
    void setup() {
        new SQLRunner(dataSource).refreshDB()
        key = grailsApplication.config.apiKey.secretkey
        basePath = "http://localhost:$port/opponent"
        url = new URL(basePath)
    }

    /**
     * convenience method for building http requests
     * @param method GET/POST
     * @param key auth key
     * @param url path to call
     * @return Http conn
     */
    HttpURLConnection returnConnection(String method, String key, URL url) {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection()
        conn.setRequestMethod(method)
        conn.doOutput = true
        conn.setRequestProperty('Content-Type', 'application/json')
        conn.setRequestProperty('X-API-KEY', key)
        if (method.toUpperCase() == 'POST') {
            conn.outputStream.withWriter { it << JsonOutput.toJson(body) }
        }
        conn
    }


    /** tests successfully returning 200 and a list of opponents for get() */
    void "return a 200 response code by get()"() {
        when: 'the base path is called for a list of opponents'
        conn = returnConnection('GET', key, url)

        and: 'we retrieve return results as JSON'
        jsonResponse = new JsonSlurper().parseText(conn.inputStream.text)

        then: 'response code is 200 and a list is returned'
        conn.responseCode == 200
        jsonResponse instanceof List
    }

    /** tests successfully returning a 403 by get() with bad auth key */
    void "return a 403 response code by get() for bad auth key"() {
        when: 'the base path is called with bad auth key'
        conn = returnConnection('GET', 'bad-key', url)

        then: 'we receive a 403 response code'
        conn.responseCode == 403
    }

    /** tests successfully returning a 200 and single opponent for getById() */
    void "return a 200 response code by getById()"() {
        when: 'getById is called'
        URL newUrl = new URL(basePath + '/1')
        conn = returnConnection('GET', key, newUrl)

        and: 'we retrieve return results as JSON'
        jsonResponse = new JsonSlurper().parseText(conn.inputStream.text)

        then: 'response code is 200 and json response is an instance of a map'
        conn.responseCode == 200
        jsonResponse instanceof Map
    }

    /** tests successfully returning a 403 by getById() with bad auth key */
    void "return a 403 response code by getById() for bad auth key"() {
        when: 'getById is called with bad auth key'
        URL newUrl = new URL(basePath + '/1')
        conn = returnConnection('GET', 'bad-key', newUrl)

        then: 'we receive a 403 response code'
        conn.responseCode == 403
    }

    /** tests successfully returning a 404 by getById() for ID not found */
    void "return a 404 by getById for ID not found"() {
        when: 'getById path is called'
        URL newUrl = new URL(basePath + '/10')
        conn = returnConnection('GET', key, newUrl)

        then: 'response code is 404'
        conn.responseCode == 404
    }

    /**  tests successfully returning a 200 by post() for opponent update */
    void "return 201 by post() for successful opponent update"() {
        when: 'an opponent receives new values'
        opponent = Opponent.get(1)
        opponent.wins == 2
        opponent.losses == 3
        opponent.totalScore == 25

        and: 'we update the opponent'
        body = opponent.returnAsMap()
        conn = returnConnection('POST', key, url)

        and: 'we retrieve return results as JSON'
        jsonResponse = new JsonSlurper().parseText(conn.inputStream.text) as Map

        then: 'the opponent is returned and values are successfully updated'
        conn.responseCode == 201
        jsonResponse.username == body.username
        jsonResponse.difficulty == body.difficulty
        jsonResponse.wins == body.wins
        jsonResponse.losses == body.losses
        jsonResponse.totalScore == body.totalScore
    }

    /** tests successfully returning a 403 by post() for bad auth key */
    void "return 403 by post() for bad auth key"() {
        when: 'we try to update an opponent with a bad auth key'
        body = Opponent.get(1).returnAsMap()
        conn = returnConnection('POST', 'bad-key', url)

        then: 'response code is 403'
        conn.responseCode == 403
    }

    /** tests successfully returning a 400 by post() for a null body */
    void "return 400 by post() for missing body"() {
        when: 'the opponent body is missing from the request'
        conn = returnConnection('POST', key, url)

        then: 'response code is 400'
        conn.responseCode == 400
    }

    /** tests successfully returning a 500 by post() for invalid opponent */
    void "return 500 by post() for invalid opponent"() {
        when: 'the opponent body is invalid'
        opponent = Opponent.get(1)
        opponent.difficulty = 0

        body = opponent.returnAsMap()
        conn = returnConnection('POST', key, url)

        then: 'response code is 500'
        conn.responseCode == 500
    }
}