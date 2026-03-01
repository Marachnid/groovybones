import groovybones.GameBoard

/*
    This script acts like a main method controlling/orchestrating game logic for testing
 */


final Random r = new Random()
int dice


GameBoard test1 = new GameBoard(boardName: 'player1')
GameBoard test2 = new GameBoard(boardName: 'player2')

boolean runGame = true


while (runGame) {
    dice = r.nextInt(6) + 1
    test1.runBoard(dice)

    if (test1.detectFullBoard()) {
        break
    } else {
        dice = r.nextInt(6) + 1
        test2.runBoard(dice)
    }
}


println test1.board
println test2.board

test1.calculateScore()
test2.calculateScore()


println test1.score
println test2.score