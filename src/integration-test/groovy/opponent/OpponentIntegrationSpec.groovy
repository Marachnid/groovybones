package opponent

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.*
import groovybones.OpponentService
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise
import util.SQLRunner
import javax.sql.DataSource


/** Carries out integration tests for Opponent domain persistence operations */
@Integration
@Rollback
@Stepwise
class OpponentIntegrationSpec extends Specification {
    @Shared
    DataSource dataSource

    Opponent opponent
    OpponentService opService


    /** basic setup method to instantiate objects and refresh test schema*/
    void setup() {
        new SQLRunner(dataSource).refreshDB()

        opService = new OpponentService()
        opponent = new Opponent(username: 'tester', difficulty: 1, wins: 0, losses: 0, totalScore: 0)
                .save(failOnError: true)
    }

    /** tests that opponent difficulty update is ignored */
    void "opponentUpdate() ignores updates to difficulty"() {
        when: 'opponent difficulty is changed'
        opponent.difficulty = 2

        and: 'the opponent is updated'
        opponent = opService.updateOpponent(opponent)

        then: 'opponent difficulty remains unchanged'
        opponent.difficulty == 1
    }

    /** tests an update to username fails to find existing user */
    void "updateOpponent() fails to find username to update"() {
        when: 'opponent name is changed'
        opponent.username = 'tester2'

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
        newOp.wins == opponent.wins
        newOp.losses == opponent.losses
        newOp.totalScore == opponent.totalScore
        newOp.difficulty != opponent.difficulty
    }
}