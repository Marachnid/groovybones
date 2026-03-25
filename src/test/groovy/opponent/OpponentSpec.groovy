package opponent

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

/** Tests Opponent instance methods */
class OpponentSpec extends Specification implements DomainUnitTest<Opponent> {
    Opponent opponent

    /** basic setup method */
    void setup() {
        opponent = new Opponent(username: 'Chug Chug', difficulty: 1, wins: 0, losses: 0, totalScore: 0)
    }

    /**
     * tests returning opponent instance variables as a map
     * @return pass/fail
     */
    def "opponentAsMap() returns correct opponent instance variables"() {
        when: 'opponent instance variables are extracted to a map'
        Map map = opponent.opponentAsMap()

        then: 'all values should match'
        map.username == opponent.username
        map.difficulty == opponent.difficulty
        map.wins == opponent.wins
        map.losses == opponent.losses
        map.totalScore == opponent.totalScore
    }
}