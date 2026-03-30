package opponent

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.*
import groovybones.Opponent
import groovybones.opponent.OpponentService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise
import util.SQLRunner
import javax.sql.DataSource


/** Performs integration tests on OpponentService persistence methods */
@Integration
@Rollback
@Stepwise
class OpponentServiceIntegrationSpec extends Specification {
    private static final Logger log = LoggerFactory.getLogger(OpponentRetrieverIntegrationSpec)

    @Shared
    DataSource dataSource

    Opponent opponent
    OpponentService opService


    /** basic setup method to instantiate objects and refresh test schema*/
    void setup() {
        log.info('Running Integration Tests')
        new SQLRunner(dataSource).refreshDB()

        opService = new OpponentService()
        opponent = Opponent.get(1)
        log.info("Opponent Properties: ${opponent.properties} - OpponentID: ${opponent.id}")
    }

    /** tests that opponent difficulty update is ignored */
    void "updateOpponent() ignores updates to difficulty"() {
        when: 'opponent difficulty is changed'
        opponent.difficulty = 2

        and: 'the opponent is updated'
        opponent = opService.updateOpponent(opponent)

        then: 'opponent difficulty remains unchanged'
        opponent.difficulty == 1
    }

    /** tests an invalid id fails to find existing user */
    void "updateOpponent() fails to find id to update"() {
        when: 'opponent id is invalid'
        opponent.id = 100

        and: 'opponent tries to update'
        opponent = opService.updateOpponent(opponent)

        then: 'updateOpponent() throws null exception'
        thrown(NullPointerException)
    }

    /** tests successfully updating opponent wins, losses, totalScore */
    void "updateOpponent() successfully updates opponent"() {
        when: 'opponent wins, losses, totalScore is updated'
        opponent.wins = 1
        opponent.losses = 1
        opponent.totalScore = 1

        and: 'opponent is updated and reassigned to a temp object'
        Opponent newOp = opService.updateOpponent(opponent)

        then: 'temp opponent should match wins, losses, totalScore'
        newOp.id == opponent.id
        newOp.wins == opponent.wins
        newOp.losses == opponent.losses
        newOp.totalScore == opponent.totalScore
    }

    /** tests overall successful update that results in difficulty ignored */
    void "updateOpponent() updates wins, losses, totalScore and ignores difficulty"() {
        when: 'opponent wins, losses, totalScore, and difficulty is updated'
        opponent.wins = 1
        opponent.losses = 1
        opponent.totalScore = 1
        opponent.difficulty = 2

        and: 'opponent is updated and reassigned to a temp object'
        Opponent newOp = opService.updateOpponent(opponent)

        then: 'temp opponent should match wins, losses, totalScore, but not difficulty'
        newOp.id == opponent.id
        newOp.wins == opponent.wins
        newOp.losses == opponent.losses
        newOp.totalScore == opponent.totalScore
        newOp.difficulty != opponent.difficulty
    }
}