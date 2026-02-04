

/*
    Arrays.asList() locks array sizes - one array with 3 inner arrays
    def inner might be a Groovy quirk
        Arrays.asList(null) is the preferred structure since we're reassigning placeholder nulls to [v]
        However, it fails to initialize - throwing a null pointer exception
        It might be how Arrays.asList() is resolving in Groovy vs. Java
*/
def inner = Arrays.asList(null, null, null)
def player1Board = Arrays.asList(inner, inner, inner)
def player2Board = Arrays.asList(inner, inner, inner)


println 'player1Board initialized'
println player1Board



/**
 * handles adding numbers when initialized values might == null
 */
def addNumber = {def board, def i, def v ->
//    board[i][0] == null ? board[i] = [v] : board[i] << v

    if (board[i][0] == null) {

        board[i] = [v]
        println 'NULL -- Reassigning value'
        println board[i]

    } else {

        board[i] << v
        println 'NOT NULL -- Appending value'
        println board[i]
    }
}


def deleteNumber = {def board, def i, def v ->

   if (board[i].contains(v)) {
       println 'The board contains ' + v
       println board

       board[i].removeAll {it == v}

   }
    return board
}


def test = {

    def randomNumber = 2


    addNumber(player1Board, 0, 2)
    addNumber(player1Board, 0, 2)
    addNumber(player1Board, 0, 1)
    addNumber(player1Board, 2, 4)
    addNumber(player1Board, 1, 3)

    println '\n\n--------------------------'
    println 'player1Board after adding'
    println player1Board


    player1Board = deleteNumber(player1Board, 0, randomNumber)

    println '\n\n--------------------------'
    println 'player1Board after deleting'
    println player1Board


//    player1Board.eachWithIndex {column, i ->
//        if (column.count(randomNumber) > 0) {
////            println column
////            println i
//
//            if (player2Board[i] == null) {
//                player2Board[i] = [randomNumber]
//            } else {
//                addNumber(player2Board, i, randomNumber)
//            }
//            player1Board[i].removeAll {it == randomNumber}
//        }
//    }


}.call()
