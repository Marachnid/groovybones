package groovybones


class GameBoard {

    //TODO will eventually have to make this dynamic to allow custom board sizes
    ArrayList board = [[], [], []]
    final int columnMaxSize = 3

    //board score is updated every time a number is added or deleted
    int score


    /**
     * Appends a new value to a board's column if expected column.size <= max
     * @param i index position of column adding to
     * @param number number to add to board column - playerBoard[i]
     * @return boolean true if append, false if not
     */
    boolean addNumber(int i, int number) {
        if (board[i].size() + 1 <= columnMaxSize) board[i] <<number
        else false

        calculateScore()
    }


    /**
     * Deletes all values of a column matching the targeted number to delete
     * @param i index position of column deleting from
     * @param number number to delete
     * @return boolean true if deleted, false if not
     */
    boolean deleteNumber(int i, int number) {
        if (board[i].contains(number)) board[i].removeAll {it == number}
        else false

        calculateScore()
    }


    /**
     * Maps board values to determine all numbers present
     * determines a number's column index position and how many times it's repeated per column (index)
     * repeated numbers are counted twice, let Set filter out duplications
     * @return Set of numbers present, their index, and number of repetitions
     */
    Set mapBoardValues() {
        Set values = []

        board.eachWithIndex {column, i ->
            column.each {v ->
                values << [index: i, number: v, repetitions: column.count(v)]
            }
        }
        return values
    }


    /**
     * Calculates a board's score
     * numbers are exponentially multiplied by their value and # of repetitions
     * repeated values in the same column are more valuable (col 1[3:1] == 3, col 2[3:2] == 9, etc...)
     * @return updated score
     */
    def calculateScore() {
        score = 0
        mapBoardValues().each {entry ->
            score += entry['number'] ** entry['repetitions']
        }
    }
}
