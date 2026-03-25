package opponent

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.*
import groovybones.OpponentService
import spock.lang.Specification

@Integration
@Rollback
class OpponentIntegrationSpec extends Specification {
    Opponent opponent
    OpponentService opService


    def setup() {
        opponent = new Opponent(username: 'tester', difficulty: 1, wins: 0, losses: 0, totalScore: 0)
                .save(failOnError: true)
    }

    def "opponentUpdate() ignores updates to difficulty"() {
        when: 'opponent difficulty is changed'
        opponent.difficulty = 2

        and: 'the opponent is updated'
        opponent = opService.updateOpponent(opponent)



        then: 'opponent difficulty remains unchanged'
        opponent.difficulty == 1
    }
}




/*




    def 'updateOpponent() will not find opponent by new username'() {
        when: 'opponent username is changed'
        opponent.username = 'tester1'

//        and: 'updateOpponent() attempts to find existing opponent'
//        opponent.updateOpponent()

        then: ''
        opponent.updateOpponent() == null
    }
 */