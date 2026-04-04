package groovybones.game

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Responsible for orchestrating opponent actions and difficulty profiles
 * wraps actions around opponent and user GameBoard references persisting through game/session lifetime
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
    GameBoard user



    /** default class constructor */
    OpponentActions() {}

    /**
     * loaded constructor to map opponentDifficulty to enum difficulty
     * @param opponentDifficulty int value corresponding to difficultyMap
     * @param opponent GameBoard object
     * @param user GameBoard object
     */
    OpponentActions(int opponentDifficulty, GameBoard opponent, GameBoard user) {
        this.opponentDifficulty = opponentDifficulty
        this.opponent = opponent
        this.user = user
        difficulty = difficultyMap
                .find { it.value == opponentDifficulty}
                .key as Difficulty

        log.info("Opponent Actions instantiated, difficulty: $difficulty")
    }


    /**
     * orchestrates opponent behavior
     * difficulty is based on sequencing actions by true/false to determine priority
     * hard and medium 'see' the user
     * easy only 'sees' its own board
     * @param dice random int value generated each turn
     * @return opponent action
     */
    def opponentOrchestrator(int dice) {
        log.info("Opponent Orchestrator running - Dice: $dice")
        final def attack = { attackBoard(dice) }
        final def stack = { stackBoard(dice) }
        final def random = { runBoardRandomly(dice) }
        def actions

        switch(difficulty) {
            case difficulty.HARD:
                actions = [attack, stack, random]       //most spiteful
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
     * deletes matching user dice in equivalent column if they exist
     * @param dice random int value generated each turn
     * @return true if can place randomly, false if not
     */
    boolean runBoardRandomly(int dice) {
        log.info('running board randomly')
        final column = r.nextInt(game.columnMaxSize)

        if (opponent.addNumber(column, dice)) {
            user.deleteNumber(column, dice)

            log.info('dice random placement 1 successful')
            return true
        }

        //try to place in the next open column if random fails
        return opponent.board.withIndex().any {col, index ->
            if (opponent.addNumber(index as int, dice)) {
                user.deleteNumber(index as int, dice)

                log.info('dice random placement 2 successful')
                true
            } else {
                log.info('dice placement failed')
                false
            }
        }
    }


    /**
     * identifies and attacks (removes) matching dice from user column by placing in equivalent opponent column
     * checks opponent board columns.size() to see if attack is possible
     * places dice in opponent column and deletes user dice in equivalent column if attack possible
     * executes attacks randomly if more than one column matches
     * @param dice random int value generated each turn
     * @return true if attack, false if not
     */
    boolean attackBoard(int dice) {
        log.info('attempting attack')
        //target user columns to attack containing dice
        ArrayList userIndexes = findIndexes(user.board as ArrayList<ArrayList>, dice)

        //remove opponent attack choices if column(s) already full
        userIndexes = mutateIndexes(opponent.board as ArrayList<ArrayList>, userIndexes)

        //if opponent columns are open, attack user columns randomly based on user column/dice instances
        if (!userIndexes.isEmpty()) {
            final int ran = r.nextInt(userIndexes.size())
            user.deleteNumber(userIndexes[ran] as int, dice)

            log.info('dice placed successfully')
            return opponent.addNumber(userIndexes[ran] as int, dice)
        } else {
            log.info('dice placement failed')
            false
        }
    }


    /**
     * identifies and stacks opponent columns with matching dice if columns available
     * checks own board columns.size() to see if stacking is possible
     * stacks randomly if more than one column containing matching dice
     * deletes matching user dice in equivalent column if they exist
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
            final int ran = r.nextInt(opponentIndexes.size())
            user.deleteNumber(opponentIndexes[ran] as int, dice)

            log.info('dice stacked successfully')
            return opponent.addNumber(opponentIndexes[ran] as int, dice)
        } else {
            log.info('dice placement failed')
            false
        }
    }


    /**
     * finds column indexes for all columns containing the dice value
     * @param dice random int rolled each user turn
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