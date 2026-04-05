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
    boolean updateOpponent(Long id, Map newValues) {
        log.info('OpponentService updateOpponent()')

        Opponent opponent = Opponent.get(id)
        if (!opponent) return false

        log.info("Oser ID: ${opponent.id} found, new values: ${newValues.toString()}")
        opponent.properties = newValues


        if (!opponent.validate()) {
            log.info('Invalid data')
            return false
        }

        opponent.save(flush: true)
        log.info("Update successful")
        true
    }
}
