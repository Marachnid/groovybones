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
     * updates Opponent properties
     * @param id opponent id
     * @param newValues updated opponent values
     */
    boolean updateOpponent(Map newValues) {
        log.info("OpponentService updateOpponent() for ID: ${newValues.id}")

        Opponent opponent = Opponent.get(newValues.id)
        if (!opponent) {
            log.info('Opponent not found')
            return false
        }

        opponent.properties = newValues
        log.info("New Opponent properties: ${opponent.properties.toString()}")

        try {
            opponent.save(flush: true, failOnError: true)
            log.info('Update successful')
            true

        } catch (e) {
            log.info("Update failed: $e")
            false
        }
    }
}