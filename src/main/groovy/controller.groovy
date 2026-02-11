import groovybones.GameBoard

/*
    This script acts like a main method controlling/orchestrating game logic for testing

 */

GameBoard player1 = new GameBoard()
GameBoard player2 = new GameBoard()

Random r = new Random()
int dice
int column

println player1.board


def test = {
    dice = r.nextInt(6) + 1
    column = r.nextInt(3)
    println 'Random number: ' + dice

    if (!player1.addNumber(column, dice)) {
        println 'column full'
        println 'finding first alternative column'

        player1.board.withIndex().any {col, index ->
            if (col.size()+1 <= 3) {
                println 'Found an open column - ' + index
                return player1.addNumber(index, dice)
            } else {
                println 'No columns are available'
                return false
            }
        }
    } else return true
}

while (test()) {
}
println player1.board
