package groovybones.user

import grails.gorm.transactions.Transactional
import groovybones.Opponent
import groovybones.SavedGame
import groovybones.User

/**
 * Responsible for handling User persistence operations
 * Users are detached from persistence layer to prevent cognitoSub ever making it to front end
 * In-memory (session) users are shallow reference copies of DB/persistence entities (GORM/Hibernate persistence entities)
 * User-driven SavedGame operations included (will only ever be user-driven)
 */
@Transactional
class UserService {

    /**
     * retrieves a user with cognitoSub nullified
     * attaches found ID to returned user
     * detaches user savedGames from persistence to keep behavior predictable
     * @param id to query and attach to reference if found
     * @return User or null if not found
     */
    User getUserById(long id) {
        User existing = User.get(id)
        User user

        if (existing) {
            user = new User(existing.returnAsMap())     //ignores pulling cognitoSub from persistence
            user.id = id

            //detach savedGames from persistence layer (will cause type problems in addSavedGame() if not)
            user.savedGames = existing.savedGames.collect { SavedGame record ->
                new SavedGame(record.properties).tap {it.id = record.id}
            }
            return user
        } else null
    }


    /**
     * updates only updatable fields of existing User entity
     * returns false if user is null or fails to validate
     * @param user entity to be updated
     * @return true for updated, else false
     */
    boolean updateUser(User user) {
        User existing = User.get(user.id)
        if (!existing) return false

        existing.username = user.username           //in event of username change in Cognito
        existing.wins = user.wins
        existing.losses = user.losses
        existing.totalScore = user.totalScore

        if (existing.validate() && existing.save(flush: true, failOnError: true)) true
        else false
    }


    /**
     * initializes and creates a new User entity with default wins/losses/totalScore
     * @param cognitoSub cognito profile token
     * @param username pulled from cognito Account ID
     * @return new user with pre-initialized wins/losses/totalScore == 0
     * @See CognitoOAuthService
     */
    User createUser(String cognitoSub, String username) {
        User user = new User(cognitoSub: cognitoSub, username: username,  wins: 0, losses: 0, totalScore: 0)

        if (user.validate()){
            user.save(flush: true, failOnError: true)
            long id = user.id

            user = new User(user.returnAsMap())
            user.id = id
            return user
        } else null
    }


    /**
     * deletes an existing DB user if found
     * deletes savedGame children if they exist
     * @param id of User entity to be deleted
     * @return true if user exists and deletion does not return error, else false
     */
    boolean deleteUser(long id) {
        User user = User.get(id)
        if (!user) return false

        //if deletion does not return errors, delete savedGame db children
        if (!user.delete(flush: true, failOnError: true)) {
            SavedGame.where { id == user.id }.deleteAll()
            true
        } else false
    }


    /**
     * add new savedGame DB entity to User
     * updates in-memory SavedGames with detached references
     * returns detached SavedGame reference
     * @param user to add and update new savedGame to
     * @param savedGame to add to user
     * @return savedGame record if successful, else null
     */
    SavedGame addSavedGame(User user, SavedGame savedGame) {
        User existing = User.get(user.id)
        if (!existing || !Opponent.get(savedGame.opponentId)) return null

        existing.addToSavedGames(savedGame)
        if (!updateUser(existing)) return null

        savedGame = new SavedGame(savedGame.properties).tap { it.id = savedGame.id }
        user.savedGames.add(savedGame)

        savedGame
    }

    /**
     * deletes a user's saved game and returns the savedGame deleted
     * @param user User to delete savedGame from
     * @param savedGame to be deleted
     * @return savedGame removed else null (might change to true/false returns)
     */
    SavedGame deleteSavedGame(User user, SavedGame savedGame) {
        SavedGame existing = SavedGame.get(savedGame.id)
        User existingUser = User.get(user.id)

        if (!existing || !existingUser) return null

        existingUser.removeFromSavedGames(existing)
        existing.delete()   //persist the delete to saved_game

        user.removeFromSavedGames(savedGame)

        if (!updateUser(existingUser)) return null
        savedGame
    }
}
