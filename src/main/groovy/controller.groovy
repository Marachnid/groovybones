import groovybones.GameBoard

/*
    This script acts like a main method controlling/orchestrating game logic for testing
 */


final Random r = new Random()
int dice

def preBoard1 = [[1,1,1], [2],[4]]
def preBoard2 = [[3], [5],[6]]

GameBoard player1 = new GameBoard(boardName: 'player1', board: preBoard1)
GameBoard player2 = new GameBoard(boardName: 'player2', board: preBoard2)

println player1.board
println player2.board

dice = 5

def boardOp(def randomNumber, GameBoard staticBoard, GameBoard activeBoard) {

    println "Player: ${staticBoard.boardName}"
    def mapValues = staticBoard.mapBoardValues()
    println mapValues

//    if (mapValues['number'].contains(randomNumber)) {
//        println randomNumber + ' NUMBER FOUND'
//        println mapValues[0]
//
//    }

    mapValues.eachWithIndex{ def entry, int i ->
        if (entry['number'] == randomNumber) {
            println 'TEST SUCCESS'
            println entry['number']
            println i
        }
    }


//    mapValues.any {
//        if (it.number == randomNumber) {
//            println 'TEST2'
//
//            if (activeBoard.addNumber(it.index, it.number)) {
//                staticBoard.deleteNumber(it.index, it.number)
//                return true
//            }
//        } else {
//            println it.number
//            activeBoard.runBoard(randomNumber)
//            println 'ELSE RUN BOARD'
//            return true
//        }
//    }
}

println 'BOARD OP START'
boardOp(dice, player2, player1)


//println player1.board
//println player2.board



















//test.any {
//
//    if (it.number == meh) {
//
//        if (test2.addNumber(it.index, it.number)) {
//            test1.deleteNumber(it.index, it.number)
//        } else {
//            test2.runBoard(meh)
//        }
//    } else {
//        test2.runBoard(meh)
//    }
//}
//
//
//def mehTest = test2.mapBoardValues()
//meh = 4
//
//
//mehTest.any {
//
//    if (it.number == meh) {
//
//        if (test1.addNumber(it.index, it.number)) {
//            test2.deleteNumber(it.index, it.number)
//        } else {
//            test1.runBoard(meh)
//        }
//    } else {
//        test1.runBoard(meh)
//    }
//}


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