import groovybones.GameBoard
import groovybones.GameRunner

/*
    This script acts like a main method controlling/orchestrating game logic for testing

 */

GameBoard player1 = new GameBoard()
GameBoard player2 = new GameBoard()


println player1.board

GameRunner runner = new GameRunner()
while (runner.runBoard(player1)) {
    //
}
println player1.board
