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
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/** Performs tests on OpponentRetriever Opponent mapping */
@Integration
@Rollback
@Stepwise
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OpponentRetrieverIntegrationSpec extends Specification {
    private static final Logger log = LoggerFactory.getLogger(OpponentRetrieverIntegrationSpec)

    @Shared
    DataSource dataSource

    @LocalServerPort
    int port

    GrailsApplication grailsApplication
    Opponent opponent
    ArrayList<Map> opponentHeaders
    String key
    String basePath


    /** basic setup method to instantiate objects and refresh test schema */
    void setup() {
        log.info('Running Integration Tests')

        new SQLRunner(dataSource).refreshDB()
        key = grailsApplication.config.getProperty('apiKey.secretkey', String)
        basePath = "http://localhost:$port/opponent"

        log.info("base testing path: $basePath")
    }


    /** tests retrieving an ArrayList<Map> of opponent headers */
    void "return mapped list of opponent headers"() {
        when: 'OpponentServiceController base GET path is called'
        OpponentRetriever opponentRetriever = new OpponentRetriever(path: basePath)
        opponentHeaders = opponentRetriever.retrieveList(key)

        then: 'response code is 200, list of returned opponents is neither null nor empty'
        opponentRetriever.responseCode == 200
        !opponentHeaders.isEmpty()
        opponentHeaders[0].id
        opponentHeaders[0].username
    }

    /** tests retrieving opponent stats */
    void "return mapped opponent"() {
        when: 'OpponentServiceController getById path is called'
        OpponentRetriever opponentRetriever = new OpponentRetriever(path: basePath)
        opponent = Opponent.get(1)
        final Map stats = opponentRetriever.retrieveOpponentStats(key, opponent.id)

        then: 'response code is 200, stats match db stats'
        opponentRetriever.responseCode == 200
        opponent.wins == stats.wins
        opponent.losses == stats.losses
        opponent.totalScore == stats.totalScore
    }

    /** tests returning null opponent if ID not found */
    void "return null opponent if ID not found"() {
        expect: 'null for unfound ID'
        !new OpponentRetriever(path: basePath).retrieveOpponentStats(key, -1)
    }


    /** tests returning null opponent if incorrect auth key */
    void "return null opponent if incorrect API key"() {
        expect: 'incorrect auth is used'
        !new OpponentRetriever(path: basePath).retrieveList('badKey')
    }

    /** tests successfully returning an updated opponent */
    void "return opponent with updated values"() {
        when: 'opponent receives a new value'
        OpponentRetriever opponentRetriever = new OpponentRetriever(path: basePath)
        opponentRetriever.postUpdate(key, 1, [totalScore: 201])

        then: 'response code is 201, totalScore is updated'
        opponentRetriever.responseCode == 201
    }
}