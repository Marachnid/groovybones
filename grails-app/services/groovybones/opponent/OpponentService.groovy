package groovybones.opponent

import grails.gorm.transactions.Transactional
import groovybones.Opponent

/**
 * Responsible for handling Opponent persistence operations
 * Opponent entities not intended to be created from/in the app
 * Opponent entities only update wins, losses, totalScore
 */
@Transactional
class OpponentService {

    /**
     * handles updating an existing opponent's wins, losses, and totalScore
     * username and difficulty are ignored
     * @return updated opponent
     */
    Opponent updateOpponent(Opponent opponent) {
        Opponent existing = Opponent.get(opponent.id)

        existing.wins = opponent.wins
        existing.losses = opponent.losses
        existing.totalScore = opponent.totalScore
        existing.save(failOnError: true)
        existing
    }
}
