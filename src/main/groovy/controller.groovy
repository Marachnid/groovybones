import groovybones.GameBoard

/*
    This script acts like a main method controlling/orchestrating game logic for testing
 */


final Random r = new Random()
int dice


GameBoard test1 = new GameBoard(boardName: 'player1')
GameBoard test2 = new GameBoard(boardName: 'player2')

boolean runGame = true


test1.board[0] << 1
test1.board[0] << 1
test1.board[0] << 1
test1.board[1] << 2
test1.board[2] << 3
test1.board[2] << 1



println test1.board


def test = test1.mapBoardValues()

println test['index']
println test['number']
println test['repetitions']

println test[0]

//if (test.any {it.number == 1}) {
//    println '1 DETECTED'
//}

def meh = 4

test.any {

    if (it.number == meh) {

        if (test2.addNumber(it.index, it.number)) {
            test1.deleteNumber(it.index, it.number)
        } else {
            test2.runBoard(meh)
        }
    } else {
        test2.runBoard(meh)
    }
}


def mehTest = test2.mapBoardValues()
meh = 4


mehTest.any {

    if (it.number == meh) {

        if (test1.addNumber(it.index, it.number)) {
            test2.deleteNumber(it.index, it.number)
        } else {
            test1.runBoard(meh)
        }
    } else {
        test1.runBoard(meh)
    }
}


println test1.board
println test2.board

//while (runGame) {
//    dice = r.nextInt(6) + 1
//    test1.runBoard(dice)
//
//    if (test1.detectFullBoard()) {
//        break
//    } else {
//        dice = r.nextInt(6) + 1
//        test2.runBoard(dice)
//    }
//}
//
//
//println test1.board
//println test2.board
//
//test1.calculateScore()
//test2.calculateScore()
//
//
//println test1.score
//println test2.score