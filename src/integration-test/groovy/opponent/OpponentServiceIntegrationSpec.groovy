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
    private static final Logger log = LoggerFactory.getLogger(OpponentServiceIntegrationSpec)

    @Shared
    DataSource dataSource

    Opponent opponent
    OpponentService opponentService
    Map newValues


    /** basic setup method to instantiate objects and refresh test schema*/
    void setup() {
        log.info('Running Integration Tests')
        new SQLRunner(dataSource).refreshDB()
    }

    /** tests an invalid id fails to find existing user */
    void "updateOpponent() returns false for unfound opponent"() {
        expect: 'opponent tries to update'
        !opponentService.updateOpponent([id: -1, wins: 10])
    }

    /** tests successfully updating opponent wins, losses, totalScore */
    void "updateOpponent() successfully updates opponent"() {
        when: 'opponent wins, losses, totalScore is updated'
        newValues = [id: 1, wins: 2, losses: 2, totalScore: 101]

        and: 'opponent is updated and reassigned to a temp object'
        final boolean updated = opponentService.updateOpponent(newValues)
        opponent = Opponent.get(1)

        then: 'temp opponent should match wins, losses, totalScore'
        updated
        opponent.wins == newValues.wins
        opponent.losses == newValues.losses
        opponent.totalScore == newValues.totalScore
    }
}