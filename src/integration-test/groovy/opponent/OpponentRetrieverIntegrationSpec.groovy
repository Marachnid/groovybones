package opponent

import grails.core.GrailsApplication
import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import groovybones.Opponent
import groovybones.opponent.OpponentRetriever
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise
import util.SQLRunner
import javax.sql.DataSource

/** Performs tests on OpponentRetriever Opponent mapping */
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
    OpponentRetriever opRet
    Opponent opponent
    String key
    String basePath


    /** basic setup method to instantiate objects and refresh test schema */
    void setup() {
        new SQLRunner(dataSource).refreshDB()
        key = grailsApplication.config.apiKey.secretkey
        basePath = "http://localhost:$port/opponent"
    }


    /** tests retrieving and mapping an ArrayList<Opponent> of opponents */
    void "return mapped list of opponents"() {
        when: 'OpponentServiceController base GET path is called'
        opRet =  new OpponentRetriever(key, basePath, false)

        then: 'response code is 200, list of returned opponents is neither null nor empty'
        opRet.responseCode == 200
        opRet.opponent != null
        !opRet.opponent.isEmpty()
    }

    /** tests retrieving and mapping a single opponent */
    void "return mapped opponent"() {
        when: 'OpponentServiceController getById path is called'
        String path = basePath + '/1/'
        opRet = new OpponentRetriever(key, path, true)

        then: 'response code is 200, mapped opponent is not null'
        opRet.responseCode == 200
        opRet.opponent != null

        //with how Groovy resolves 0 to null/false, check that min value is 0
        opRet.opponent.difficulty >= 0
        opRet.opponent.wins >= 0
        opRet.opponent.losses >= 0
        opRet.opponent.totalScore >= 0
    }

    /** tests returning null opponent if ID not found */
    void "return null opponent if ID not found"() {
        when: 'a non-existent ID is called'
        opRet = new OpponentRetriever(key, basePath + '/10', true)

        then: 'response code is 404, opponent is null'
        opRet.responseCode == 404
        opRet.opponent == null
    }

    /** tests returning null opponent if incorrect path called */
    void "return null opponent if incorrect path"() {
        when: 'incorrect path is called'
        opRet = new OpponentRetriever(key, basePath + '1', true)

        then: 'response code is 404, opponent is null'
        opRet.responseCode == 404
        opRet.opponent == null
    }

    /** tests returning null opponent if incorrect auth key */
    void "return null opponent if incorrect API key"() {
        when: 'incorrect auth is used'
        opRet = new OpponentRetriever('bad-key', basePath, true)

        then: 'response code is 403, opponent is null'
        opRet.responseCode == 403
        opRet.opponent == null
    }

    /** tests successfully returning an updated opponent */
    void "return opponent with updated values"() {
        when: 'opponent receives a new value'
        opponent = Opponent.get(1)
        opponent.totalScore = 100

        and: 'we update the opponent'
        opRet = new OpponentRetriever(key, basePath, opponent)

        then: 'response code is 201, totalScore is updated'
        opRet.responseCode == 201
        opRet.opponent.totalScore == 100
    }

    /** tests returning null opponent for failed update */
    void "return null opponent if invalid"() {
        when: 'an opponent is invalid'
        opponent = Opponent.get(1)
        opponent.id = null

        and: 'we try to update'
        opRet = new OpponentRetriever(key, basePath, opponent)

        then: 'response code is 404, opponent is null'
        opRet.responseCode == 404
        opRet.opponent == null
    }
}