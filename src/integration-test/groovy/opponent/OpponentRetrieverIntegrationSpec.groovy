package opponent

import grails.core.GrailsApplication
import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import groovybones.opponent.OpponentRetriever
import groovybones.opponent.RequestCaller
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise
import util.SQLRunner
import javax.sql.DataSource

/** Carries out integration tests for OpponentRetriever Opponent mapping */
@Integration
@Rollback
@Stepwise
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OpponentRetrieverIntegrationSpec extends Specification {
    @Shared
    DataSource dataSource

    @LocalServerPort
    int port

    GrailsApplication grailsApplication
    RequestCaller requestCaller
    OpponentRetriever opRet
    Opponent opponent
    ArrayList<Opponent> opponents
    String key
    String basePath



    /** basic setup method to instantiate objects and refresh test schema*/
    void setup() {
        new SQLRunner(dataSource).refreshDB()
        key = grailsApplication.config.apiKey.secretkey
        basePath = "http://localhost:$port/opponent"

    }

    /** tests retrieving and mapping an ArrayList<Opponent> of opponents */
    void "successfully map a list of retrieved opponents from webservice"() {
        when: 'OpponentServiceController base GET path is called'
        opRet =  new OpponentRetriever(key, basePath, false)
        opponents = opRet.opponent

        then: 'response is successful and a list of returned opponents is not empty'
        opRet.responseCode == 200
        opRet.responseBody != null
        !opponents.isEmpty()
    }

    /** tests retrieving and mapping a single opponent */
    void "successfully map a single opponent retrieved from webservice"() {
        when: 'OpponentServiceController getById path is called'
        String path = basePath + '/1/'
        opRet = new OpponentRetriever(key, path, true)
        opponent = opRet.opponent

        then: 'response is successful and a mapped opponent is returned'
        opRet.responseCode == 200
        opponent.username != null

        //with how Groovy resolves 0 to null/false, check that the value is at least 0/positive
        opponent.difficulty >= 0
        opponent.wins >= 0
        opponent.losses >= 0
        opponent.totalScore >= 0
    }

    /** tests returning null opponent if ID not found */
    void "opponent is null no ID found"() {
        when: 'a non-existent ID is called'
        String path = basePath + '/10/'
        opRet = new OpponentRetriever(key, path, true)

        then: 'response code == 404'
        opRet.responseCode == 404
        opRet.opponent == null
    }

    /** tests returning null opponent if incorrect path called */
    void "opponent is null if incorrect path"() {
        when: 'incorrect path is called'
        String path = basePath + '1'
        opRet = new OpponentRetriever(key, path, true)

        then:
        opRet.responseCode == 404
        opRet.opponent == null
    }

    /** tests returning null opponent if incorrect auth key */
    void "opponent is null if incorrect API key"() {
        when: 'incorrect auth is used'
        opRet = new OpponentRetriever('incorrect', basePath, true)

        then:
        opRet.responseCode == 403
        opRet.opponent == null
    }
}