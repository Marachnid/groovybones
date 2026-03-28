//package groovybones.user
//
//import grails.gorm.transactions.Transactional
//import groovybones.Opponent
//import groovybones.SavedGame
//import groovybones.User
//
//
///**
// * Performs persistence operations on SavedGame driven by user interactions
// */
//@Transactional
//class UserSavedGameService {
//
//    /**
//     * create a new saved game referencing both user and opponent
//     * @param user User
//     * @param opponent Opponent
//     * @param userBoard user gameBoard
//     * @param opponentBoard opponent gameBoard
//     * @param turn game turn
//     * @return updated user
//     */
//    User createSavedGame(User user, Opponent opponent, String userBoard, String opponentBoard, int turn) {
//        SavedGame savedGame = new SavedGame(
//                user: user,
//                opponent: opponent,
//                userId: user.id,
//                opponentId: opponent.id,
//                userBoard: userBoard,
//                opponentBoard: opponentBoard,
//                turn: turn
//        )
//        user.addToSavedGames(savedGame)
//        new UserService().updateUser(user)
//    }
//
//    /**
//     * deletes a user's saved game and returns the savedGame deleted
//     * @param user User
//     * @param opponent Opponent
//     * @param userBoard user gameBoard
//     * @param opponentBoard opponent gameBoard
//     * @param turn game turn
//     * @return savedGame removed
//     */
//    SavedGame deleteSavedGame(User user, SavedGame savedGame) {
//        user.removeFromSavedGames(savedGame)
//        new UserService().updateUser(user)
//        savedGame
//    }
//}