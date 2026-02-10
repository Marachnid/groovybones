//this locks the overall board columns from exceeding 3, but inner arrays can be reassigned at will
def inner = Arrays.asList(null, null, null)
def player1Board = Arrays.asList(inner, inner, inner)
def player2Board = Arrays.asList(inner, inner, inner)


/**
 * adds numbers to board - reassigns initial null arrays if true
 * TEMP workaround else if condition to block attempts at exceeding column size limit
 * columns can be overwritten without throwing an exception, but the number of columns can't be overwritten
 */
def addNumber = {def board, int i, int v ->

    final int maxColumnSize = 3
    final int potentialBoardSize = board[i].size() + 1

    if (board[i][0] == null) {
        board[i] = [v]
//        println "NULL -- Reassigning to [$v]"

    } else if ((potentialBoardSize) <= maxColumnSize) {
        board[i] << v
//        println "NOT NULL -- Appending $v"


    } else {
        println 'WARNING: COLUMN IS FULL'
    }
}


/**
 * deletes all numbers from a boards' column (board[i]) if they match value to be deleted
 */
def deleteNumber = {def board, def i, def v ->

    if (board[i].contains(v)) {
//        println "Deleting $v from board"
        board[i].removeAll {it == v}

    }
}


def boardInitializer = {
//    def randomNumber = 2

    addNumber(player1Board, 0, 4)
    addNumber(player1Board, 1, 2)
    addNumber(player1Board, 1, 2)
    addNumber(player1Board, 0, 1)
    addNumber(player1Board, 2, 2)
    addNumber(player1Board, 2, 2)


    println 'Player1Board:'
    println player1Board

    println 'Player2Board:'
    println player2Board

    println '\n\n'

}.call()




def test = {
    def randomNumber = 5
    def count = [:]

    player1Board.eachWithIndex {column, i ->

        if (column.contains(randomNumber)) {
            println "--> Player2 noticed Player1 has a $randomNumber in column: ${(i +1)}"
            count << [(i):column.count(randomNumber)]

        } else {
            println "--> Player2 didn't notice $randomNumber matching any values in Player1's board's column (${(i +1)})"

        }
    }


    /*
        GAME DIFFICULTY LOGIC, if count !empty, 50/50 chance attack vs randomly adding
     */
    if (!count.isEmpty()) {
        //largestMatch seems to default to the first largest match, so if [[2,2],[2,2]], the first array will be selected
        //could be exploitable game behavior but the scenario to exploit that would be hard for users to find
        println '\n\nMax value in Count'
        def largestMatch = count.max {it.value}

        def key = largestMatch.key
        def value = largestMatch.value

        println "Column Number: ${key + 1}, Times Repeated: $value"

        println "--> Player2 noticed Player1 has $value dice of $randomNumber in column ${key + 1}"
        println "--> Player2 adds $randomNumber to their column # ${key + 1}"
        addNumber(player2Board, key, randomNumber)

        println "--> Player1's column ${key + 1} has it's own #$randomNumber dice removed from their board"
        deleteNumber(player1Board, key, randomNumber)

    } else {
        def r = new Random()
        r = r.nextInt(3)
        println 'No matches found'
        println r

//        if ((player2Board[r].size() + 1) <= 3) {
//        } else {println 'MEH'}

        println player2Board[r].size()

        if (player2Board[r].contains(null) && player2Board[r].size() + 1 <= 3) {
            println 'success'
        } else {
            println 'meh'
        }

//        addNumber(player2Board, r , randomNumber)



    }


    println '\nPlayer1\'s Board:'
    println player1Board
    println ''
    println 'Player2\'s Board:'
    println player2Board



}.call()