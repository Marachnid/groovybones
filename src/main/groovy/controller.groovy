import groovybones.GameBoard
import groovybones.GameRunner

/*
    This script acts like a main method controlling/orchestrating game logic for testing
 */

//final def board1 = [[1,2,3],[4,5,4],[3,1]]
//GameBoard player1 = new GameBoard(boardName: 'player1', board: board1)

GameBoard player1 = new GameBoard(boardName: 'player1')
GameBoard player2 = new GameBoard(boardName: 'player2')


println 'Player 1 Board: ' + player1.board
println 'Player 2 Board: ' + player2.board


boolean flag = true
ArrayList<GameBoard> players = [player1, player2]



//println player1.runBoard(player1)
//println player1.runBoard(player1)

GameBoard temp

while (flag) {

//    flag = player1.runBoard(player1)
//
//    if (flag) flag = player2.runBoard(player2)
//    else break

    if (player1.runBoard(player1)) {
        temp = player1

        println player1.board
        println temp.board

        if (!temp.runBoard(temp)) {
            flag = false
        } else {
            player2.runBoard(player2)
        }
    }

}



/*
    TODO THE MAIN PROBLEM
    The loop 'overruns' because if the first board fills up completely, it still returns true
    and allows the second to still execute
    We would have to have a method to pre-detect if the first board is full before adding, or the inverse of checking
    a board after it has been added to

 */


println 'Player 1 Board: ' + player1.board
println 'Player 2 Board: ' + player2.board



//println "Player 1 Score: ${player1.score}"
//println "Player 2 Score: ${player2.score}"


