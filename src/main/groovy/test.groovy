/*
    TESTING how to assess and interact with a gameboard (2d array)
    Starts with a 2d array initialized with 3 empty inner arrays

    gameBoard = [
        [],                 column1
        [],                 column2
        []                  column3
    ]

    New values are always shuffled to the lowest available position of a column
        *we don't have to worry about [2, , 3] --- game assumes [2,3]
        *column value order doesn't matter, only what the column contains

    To calculate total value of a player's board (and columns):
        iterate through gameBoard
        iterate through all 3 inner arrays
        we check the present number of the column and use column.count(number) to find
            number of times the present number is repeated in the same column
        we format the number and repetitions as a map object [number:column.count(number)]
        we ADD the map object to a SET to filter out repeated map objects

        THEN
        iterate through SET of map objects and exponentially multiply key.toInteger() by value
        total += k.toInteger() ** v

        items with no duplication are unchanged -- multiplied by ^1
 */


//def player1Board = [
//        [null],         //column 1
//        [null],         //column 3
//        [null]          //column 2
//]
//
//
//def player2Board = [
//        [null],         //column 1
//        [null],         //column 3
//        [null]          //column 2
//]

//Arrays.asList() locks array sizes - one array with 3 inner arrays
def inner = Arrays.asList(null, null, null)
def player1Board = Arrays.asList(inner, inner, inner)
def player2Board = Arrays.asList(inner, inner, inner)

println player1Board

/**
 * reads game board for values present and # of duplications if present
 * returns a set of all board values present and # of duplications
 * USEFUL for game logic determining hostile actions against stacked values (duplicates)
 */
def findRepeatedValues = { def gameBoard ->
    Set columnValues = []

    //iterate over outer gameBoard array
    gameBoard.each { column ->

        //iterate through inner gameBoard arrays (columns), add values and # of duplications to Set
        column.each { number ->
            if (number != null) {columnValues << ["$number" : column.count(number)]}
        }
    }
    return columnValues
}


/**
 * iterates over Set of unique board values and exponentially multiplies them
 */
def calculateValues = { def boardValues ->
    final def columnValues = findRepeatedValues(boardValues)
    def total = 0

    columnValues.each {mapItem ->
        mapItem.each{k,v -> total+= k.toInteger() ** v}
    }

    return total
}


/**
 * handles adding numbers when initialized values might == null
 */
def addNumber = {def board, def i, def v ->
    board[i][0] == null ? board[i] = [v] : board[i] << v
}



/*
    DON'T mix/match findRepeatedValues() and calculateValues()
 */
def test = {

    def randomNumber = 2


    addNumber(player1Board, 0, 2)
    addNumber(player1Board, 0, 1)
    addNumber(player1Board, 2, 4)
    addNumber(player1Board, 1, 3)

    println player1Board



    player1Board.eachWithIndex {column, i ->
        if (column.count(randomNumber) > 0) {
            println column
            println i

            if (player2Board[i] == null) {
                player2Board[i] = [randomNumber]
            } else {
//                player2Board[i] << randomNumber
                addNumber(player2Board, i, randomNumber)
            }
            player1Board[i].removeAll {it == randomNumber}
        }
    }

    println 'player 1'
    println player1Board
    println 'player 2'
    println player2Board



}.call()