package groovybones

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Class defines the gameboard used to play GroovyBones
 * Defines a board object and all related gameplay operations against board for both player and game-ai opponent
 * Defines and orchestrates opponent difficulty, actions, and priorities
 */
class GameBoard {
    private static final Logger log = LoggerFactory.getLogger(GameBoard)

    final Random r = new Random()
    final int columnMaxSize = 3
    final int range = 6
    ArrayList board = [[], [], []]


    /**
     * Appends a new value to a board's column if expected column.size <= max
     * @param i index position of column adding to
     * @param number number to add to board column - playerBoard[i]
     * @return boolean true if append, false if not
     */
    boolean addNumber(int index, int number) {

        if (board[index].size() + 1 <= columnMaxSize) {
            log.info("number added: $number to column: $index")
            board[index] << number
        }
        else {
            log.info("number not added: $number to column: $index")
            false
        }
    }

    /**
     * Deletes all values of a column matching the targeted number to delete
     * @param i index position of column deleting from
     * @param number number to delete
     * @return boolean true if deleted, false if not
     */
    boolean deleteNumber(int index, int number) {
        if (board[index].contains(number)) {
            log.info("all $number's deleted from column: $index")
            board[index].removeAll {it == number}
        }
        else {
            log.info("number: $number not found in column: $index")
            false
        }
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
            column.each {v -> values << [index: i, number: v, repetitions: column.count(v)]}
        }
        values
    }

    /**
     * Calculates a board's score
     * numbers are exponentially multiplied by their value and # of repetitions
     * repeated values in the same column are more valuable (col 1[3:1] == 3, col 2[3:2] == 9, etc...)
     * 1's get special modifier, 1 = 10
     * @return updated score
     */
    int calculateScore() {
        if (board.every {it.isEmpty()}) return 0
        else return mapBoardValues().sum {
            if (it['number'] == 1) (it['number'] * 10) ** it['repetitions']
            else {it['number'] ** it['repetitions']}
        } as int
    }

    /**
     * detects if every inner array == max column size
     * used to prevent a game loop from overrunning
     * @return true if a board is full for game end
     */
    boolean detectFullBoard() {
        log.info('checking if board is full')
        board.every {it.size() == columnMaxSize}
    }


    /**
     * generates a random number per a set range + 1 to avoid 0
     * @return random number/int + 1
     */
    int generateRandomDice() { r.nextInt(range) + 1 }
}