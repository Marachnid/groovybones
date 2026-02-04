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


//score == 29
final def player1Board = [
        [3,3,1],        //column 1
        [],             //column 3
        [4,4,3]         //column 2
]

//score == 13
final def player2Board = [
        [3,3],          //column 1
        [2,2],          //column 2
        []              //column 3
]


/**
 * reads game board for values present and # of duplications if present
 * returns a set of all board values present and # of duplications
 * USEFUL for game logic determining hostile actions against stacked values (duplicates)
 */
def findRepeatedValues = { def gameBoard ->
    Set columnValues = []

    //iterate over outer gameBoard array
    gameBoard.eachWithIndex { column, i ->

        //for temp tracking, detects if a column is empty and just notes it
        if (column.size() == 0) {println "Column - ${i + 1} - is empty"}

        //iterate through inner gameBoard arrays (columns), add values and # of duplications to Set
        column.each { number -> columnValues << ["$number" : column.count(number)]}
    }
    return columnValues
}


/**
 * iterates over Set of unique board values and exponentially multiplies them
 */
def calculateValues = { def gameBoard ->
    final def columnValues = findRepeatedValues(gameBoard)
    def total = 0

    columnValues.each {mapItem ->
        mapItem.each{k,v -> total+= k.toInteger() ** v}
    }

    println total
    return total
}

final def player1Score = calculateValues(player1Board)
final def player2Score = calculateValues(player2Board)

if (player1Score == 29){println "Player1 Score ($player1Score) matches expected value: 29"}
else {println "Player1 Score ($player1Score) DOES NOT match expected value: 29"}

if (player2Score == 13){println "Player2 Score ($player2Score) matches expected value: 13"}
else {println "Player2 Score ($player1Score) DOES NOT match expected value: 13"}

