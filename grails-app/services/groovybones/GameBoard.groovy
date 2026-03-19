package groovybones

class GameBoard {

    final Random r = new Random()
    final int columnMaxSize = 3
    final int range = 6
    int score = 0
    ArrayList board = [[], [], []]
    String boardName


    /**
     * Appends a new value to a board's column if expected column.size <= max
     * @param i index position of column adding to
     * @param number number to add to board column - playerBoard[i]
     * @return boolean true if append, false if not
     */
    boolean addNumber(int index, int number) {
        if (board[index].size() + 1 <= columnMaxSize)
            board[index] << number
        else
            return false
    }

    /**
     * Deletes all values of a column matching the targeted number to delete
     * @param i index position of column deleting from
     * @param number number to delete
     * @return boolean true if deleted, false if not
     */
    boolean deleteNumber(int index, int number) {
        if (board[index].contains(number)) board[index].removeAll {it == number}
        else return false
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
     * RESET score to 0 each call - will append to old scores otherwise
     * @return updated score
     */
    int calculateScore() {

        //TODO - temporary, have to relook at methods/game needs later
        score = 0

        mapBoardValues().each {entry ->
            score += entry['number'] ** entry['repetitions']
        }

        return score
    }

    /**
     * detects if every inner array == max column size
     * used to prevent a game loop from overrunning
     * @return true if a board is full for game end
     */
    boolean detectFullBoard() { board.every {it.size() == columnMaxSize} }


    /**
     * generates a random number per a set range
     * @return random number/int
     */
    int generateRandomDice() { r.nextInt(range) + 1 }


    /**
     * responsible for placing opponent dice randomly
     * deletes matching player dice in column if they exist
     * @param dice int value generated each turn
     * @return true if can place randomly, false if not
     */
    boolean runBoardRandomly(int dice, GameBoard player) {
        int column = r.nextInt(3)

        if (addNumber(column, dice)) {
            player.deleteNumber(column, dice)
            return true
        } else {
            board.withIndex().any {col, index ->
                if (addNumber(index as int, dice)) player.deleteNumber(index as int, dice)
                else return false
            }
        }
    }


    /**
     * allows opponent to identify player board columns containing dice value
     * checks opponent board columns.size() to see if attack is possible
     * places dice in opponent column and deletes player dice if attack possible
     * executes attacks randomly if more than one column matches
     * @param dice int value generated each turn
     * @param player GameBoard object to attack
     * @return true if attack, false if not
     */
    boolean attackBoard(int dice, GameBoard player) {

        //target player columns containing dice value
        ArrayList playerIndexes = player.findIndexes(dice)

        //check to see if columns are already full before attacking
        playerIndexes = mutateIndexes(playerIndexes)


        //if columns are open, attack randomly based on player column/dice instances
        if (!playerIndexes.isEmpty()) {
            int ran = r.nextInt(playerIndexes.size())
            player.deleteNumber(playerIndexes[ran] as int, dice)
            return addNumber(playerIndexes[ran] as int, dice)
        } else return false
    }


    /**
     * allows opponent to identify own columns containing dice value
     * checks own board columns.size() to see if stacking is possible
     * stacks randomly if more than one column containing dice
     * deletes matching player dice in column if they exist
     * @param dice int value generated each turn
     * @param player GameBoard object to delete from
     * @return true if stack, false if not
     */
    boolean stackBoard(int dice, GameBoard player) {
        ArrayList opponentIndexes = findIndexes(dice)

        //check to see if columns are already full before stacking
        opponentIndexes = mutateIndexes(opponentIndexes)


        //if columns are open, stack randomly based on opponent column/dice instances
        if (!opponentIndexes.isEmpty()) {
            int ran = r.nextInt(opponentIndexes.size())
            player.deleteNumber(opponentIndexes[ran] as int, dice)
            return addNumber(opponentIndexes[ran] as int, dice)
        } else return false
    }


    /**
     * finds column indexes for all columns containing the dice value
     * @param dice random int rolled each player turn
     * @return arrayList of columns containing dice, may be empty
     */
    ArrayList findIndexes(int dice) { board.findIndexValues {it.contains(dice)} }


    /**
     * responsible for mutating index lists against the current object board
     * for every column that is full, the matching index is removed from indexes
     * empty index list represents all board columns are full where matches exist
     * @param indexes list of indexes for columns that contain a dice value
     * @return indexes - minus any indexes of full columns
     */
    ArrayList mutateIndexes(ArrayList indexes) {
        board.eachWithIndex {column, index ->
            if (column.size() == columnMaxSize) indexes -= index
        }

        return indexes
    }
}