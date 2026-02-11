import groovybones.GameBoard

/*
    This script acts like a main method controlling/orchestrating game logic for testing

 */

GameBoard player1 = new GameBoard()

println player1.score

player1.addNumber(0, 2)
player1.addNumber(1, 3)
player1.addNumber(2, 2)
player1.addNumber(2, 2)

println player1.board
println player1.score