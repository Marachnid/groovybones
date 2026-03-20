package groovybones

/**
 * Responsible for orchestrating opponent actions and difficulty profiles
 * wraps actions around opponent and player GameBoard references persisting through game/session lifetime
 * opponent difficulty is based on ranking action priority and availability of actions
 */
class OpponentActions {
    final enum Difficulty {easy, medium, hard}
    final Random r = new Random()
    final GameBoard game = new GameBoard()  //utility purposes
    Difficulty difficulty
    GameBoard opponent
    GameBoard player


    /**
     * orchestrates opponent behavior
     * difficulty is based on sequencing actions by true/false to determine priority
     * hard and medium 'see' the player
     * easy only 'sees' its own board
     * @param dice random int value generated each turn
     * @return opponent action
     */
    def opponentOrchestrator(int dice) {
        def attack = { attackBoard(dice) }
        def stack = { stackBoard(dice) }
        def random = { runBoardRandomly(dice) }

        //TODO it might be interesting to replace selectable difficulty with opponent avatars of fixed difficulty
        def actions
        switch(difficulty) {
            case difficulty.hard:
                actions = [attack, stack, random]       //a bastard to play against
                break
            case difficulty.medium:
                actions = [stack, attack, random]       //might actually be the most difficult
                break
            case difficulty.easy:
                actions = [stack, random]               //most selfish
                break
        }
        actions.any { it() }
    }


    /**
     * responsible for placing opponent dice randomly
     * deletes matching player dice in equivalent column if they exist
     * @param dice random int value generated each turn
     * @return true if can place randomly, false if not
     */
    boolean runBoardRandomly(int dice) {
        int column = r.nextInt(game.columnMaxSize)

        if (opponent.addNumber(column, dice)) {
            player.deleteNumber(column, dice)
            return true
        }

        //try to place in the next open column if random fails
        return opponent.board.withIndex().any {col, index ->
            if (opponent.addNumber(index as int, dice)) {
                player.deleteNumber(index as int, dice)
                true
            } else false
        }
    }


    /**
     * identifies and attacks (removes) matching dice from player column by placing in equivalent opponent column
     * checks opponent board columns.size() to see if attack is possible
     * places dice in opponent column and deletes player dice in equivalent column if attack possible
     * executes attacks randomly if more than one column matches
     * @param dice random int value generated each turn
     * @return true if attack, false if not
     */
    boolean attackBoard(int dice) {
        //target player columns to attack containing dice
        ArrayList playerIndexes = findIndexes(player.board as ArrayList<ArrayList>, dice)

        //remove opponent attack choices if column(s) already full
        playerIndexes = mutateIndexes(opponent.board as ArrayList<ArrayList>, playerIndexes)

        //if opponent columns are open, attack player columns randomly based on player column/dice instances
        if (!playerIndexes.isEmpty()) {
            int ran = r.nextInt(playerIndexes.size())
            player.deleteNumber(playerIndexes[ran] as int, dice)
            return opponent.addNumber(playerIndexes[ran] as int, dice)
        } else false
    }


    /**
     * identifies and stacks opponent columns with matching dice if columns available
     * checks own board columns.size() to see if stacking is possible
     * stacks randomly if more than one column containing matching dice
     * deletes matching player dice in equivalent column if they exist
     * @param dice random int value generated each turn
     * @return true if stack, false if not
     */
    boolean stackBoard(int dice) {
        //target opponent columns to stack containing dice
        ArrayList opponentIndexes = findIndexes(opponent.board as ArrayList<ArrayList>, dice)

        //remove opponent stack choices if column(s) already full
        opponentIndexes = mutateIndexes(opponent.board as ArrayList<ArrayList>, opponentIndexes)

        //if columns are open, stack randomly based on opponent column/dice instances
        if (!opponentIndexes.isEmpty()) {
            int ran = r.nextInt(opponentIndexes.size())
            player.deleteNumber(opponentIndexes[ran] as int, dice)
            return opponent.addNumber(opponentIndexes[ran] as int, dice)
        } else false
    }


    /**
     * finds column indexes for all columns containing the dice value
     * @param dice random int rolled each player turn
     * @return arrayList of columns containing dice, may be empty
     */
    static ArrayList findIndexes(ArrayList<ArrayList> board, int dice) {
        board.findIndexValues {it.contains(dice)}
    }


    /**
     * responsible for mutating index lists against the current object board
     * for each full column, the matching index is removed from indexes
     * empty index list represents all board columns are full where matches exist
     * @param indexes list of indexes for columns that contain a dice value
     * @return indexes - minus any indexes of full columns
     */
    ArrayList mutateIndexes(ArrayList<ArrayList> board, ArrayList indexes) {
        board.eachWithIndex {column, index ->
            if (column.size() == game.columnMaxSize) indexes -= index
        }
        indexes
    }
}