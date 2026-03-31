package groovybones

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Responsible for orchestrating opponent actions and difficulty profiles
 * wraps actions around opponent and player GameBoard references persisting through game/session lifetime
 * opponent difficulty is based on ranking action priority and availability of actions
 */
class OpponentActions {
    private static final Logger log = LoggerFactory.getLogger(OpponentActions)

    final enum Difficulty {EASY, MEDIUM, HARD}
    final Map difficultyMap = [(Difficulty.EASY): 1, (Difficulty.MEDIUM): 2, (Difficulty.HARD): 3]

    final GameBoard game = new GameBoard()  //utility purposes
    final Random r = new Random()

    Difficulty difficulty
    int opponentDifficulty
    GameBoard opponent
    GameBoard player



    /** default class constructor */
    OpponentActions() {}

    /**
     * loaded constructor to map opponentDifficulty to enum difficulty
     * @param opponentDifficulty int value corresponding to difficultyMap
     * @param opponent GameBoard object
     * @param player GameBoard object
     */
    OpponentActions(int opponentDifficulty, GameBoard opponent, GameBoard player) {
        this.opponentDifficulty = opponentDifficulty
        this.opponent = opponent
        this.player = player
        difficulty = difficultyMap
                .find { it.value == opponentDifficulty}
                .key as Difficulty

        log.info("Opponent Actions instantiated, difficulty: $difficulty")
    }


    /**
     * orchestrates opponent behavior
     * difficulty is based on sequencing actions by true/false to determine priority
     * hard and medium 'see' the player
     * easy only 'sees' its own board
     * @param dice random int value generated each turn
     * @return opponent action
     */
    def opponentOrchestrator(int dice) {
        log.info("Opponent Orchestrator running - Dice: $dice")
        final def attack = { attackBoard(dice) }
        final def stack = { stackBoard(dice) }
        final def random = { runBoardRandomly(dice) }
        final def actions

        switch(difficulty) {
            case difficulty.HARD:
                actions = [attack, stack, random]       //a bastard to play against
                break
            case difficulty.MEDIUM:
                actions = [stack, attack, random]       //might actually be the most difficult
                break
            case difficulty.EASY:
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
        log.info('running board randomly')
        final column = r.nextInt(game.columnMaxSize)

        if (opponent.addNumber(column, dice)) {
            player.deleteNumber(column, dice)

            log.info('dice random placement 1 successful')
            return true
        }

        //try to place in the next open column if random fails
        return opponent.board.withIndex().any {col, index ->
            if (opponent.addNumber(index as int, dice)) {
                player.deleteNumber(index as int, dice)

                log.info('dice random placement 2 successful')
                true
            } else {
                log.info('dice placement failed')
                false
            }
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
        log.info('attempting attack')
        //target player columns to attack containing dice
        ArrayList playerIndexes = findIndexes(player.board as ArrayList<ArrayList>, dice)

        //remove opponent attack choices if column(s) already full
        playerIndexes = mutateIndexes(opponent.board as ArrayList<ArrayList>, playerIndexes)

        //if opponent columns are open, attack player columns randomly based on player column/dice instances
        if (!playerIndexes.isEmpty()) {
            int ran = r.nextInt(playerIndexes.size())
            player.deleteNumber(playerIndexes[ran] as int, dice)

            log.info('dice placed successfully')
            return opponent.addNumber(playerIndexes[ran] as int, dice)
        } else {
            log.info('dice placement failed')
            false
        }
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

            log.info('dice stacked successfully')
            return opponent.addNumber(opponentIndexes[ran] as int, dice)
        } else {
            log.info('dice placement failed')
            false
        }
    }


    /**
     * finds column indexes for all columns containing the dice value
     * @param dice random int rolled each player turn
     * @return arrayList of columns containing dice, may be empty
     */
    static ArrayList findIndexes(ArrayList<ArrayList> board, int dice) {
        log.info('finding matches')
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
        log.info('removing match choices where column is full')
        board.eachWithIndex {column, index ->
            if (column.size() == game.columnMaxSize) indexes -= index
        }
        indexes
    }
}