package groovybones.user

import grails.gorm.transactions.Transactional
import groovybones.Opponent
import groovybones.SavedGame
import groovybones.User

/**
 * Responsible for handling User persistence operations
 * Includes user-driven SavedGame operations
 */
@Transactional
class UserService {

    /**
     * retrieves a user without cognitoSub
     * attaches found ID to returned user
     * @param id to query and attach to reference if found
     * @return User or null if not found
     */
    User getUserById(long id) {
        User user = User.get(id)

        if (user) {
            user = new User(user.returnAsMap())
            user.id = id
        } else user = null
        user
    }


    /**
     * updates a User entity
     * returns false if user is null or fails to validate
     * @param user entity to be updated
     * @return true for updated, else false
     */
    boolean updateUser(User user) {
        User existing = User.get(user.id)

        if (!existing) return false

        existing.username = user.username
        existing.wins = user.wins
        existing.losses = user.losses
        existing.totalScore = user.totalScore

        if (existing.validate()) existing.save(flush: true, failOnError: true)
        else false
    }


    /**
     * initializes and creates a new User entity
     * @param cognitoSub token parsed from Cognito account ID
     * @param username username pulled from Account ID
     * @return new user with pre-initialized wins/losses/totalScore = 0
     */
    User createUser(String cognitoSub, String username) {
        User user = new User(cognitoSub: cognitoSub, username: username,  wins: 0, losses: 0, totalScore: 0)

        if (user.save(flush: true, failOnError: true)) {
            user.cognitoSub = null                  //nullify cognito sub on return - in case of reserialization on accident
        } else user = null
        user
    }


    /**
     * deletes an existing DB entity
     * @param id id to be deleted
     * @return deleted user or throws exception if can't find user
     */
    boolean deleteUser(def id) {
        User user = new User(User.get(1) as Map)
        User.get(id as int).delete(failOnError: true)
    }

    /**
     * create a new saved game referencing both user and opponent
     * @param user User
     * @param opponent Opponent
     * @param userBoard user gameBoard
     * @param opponentBoard opponent gameBoard
     * @param turn game turn
     * @return updated user
     */
    User createSavedGame(User user, Opponent opponent, String userBoard, String opponentBoard, int turn) {
        SavedGame savedGame = new SavedGame(
                user: user,
                opponent: opponent,
                userId: user.id,
                opponentId: opponent.id,
                userBoard: userBoard,
                opponentBoard: opponentBoard,
                turn: turn
        )
        user.addToSavedGames(savedGame)
        updateUser(user) as User
    }

    /**
     * deletes a user's saved game and returns the savedGame deleted
     * @param user User
     * @param opponent Opponent
     * @param userBoard user gameBoard
     * @param opponentBoard opponent gameBoard
     * @param turn game turn
     * @return savedGame removed
     */
    SavedGame deleteSavedGame(User user, SavedGame savedGame) {
        user.removeFromSavedGames(savedGame)
        updateUser(user)
        savedGame
    }
}
