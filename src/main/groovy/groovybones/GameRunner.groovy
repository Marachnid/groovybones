package groovybones

class GameRunner {


    /**
     * Simulates a player placing dice on their board until no more can be placed
     * if the current column if full, it seeks to fill the next available column
     * the loop ends once all columns are full
     * @param board GameBoard object representing a player board
     * @return true if looping, false if not
     */
    static boolean runBoard(GameBoard board) {
        final Random r = new Random()
        int dice = r.nextInt(6) + 1
        int column = r.nextInt(3)
        println "Random dice: $dice --- Random column: $column"


        if (!board.addNumber(column, dice)) {
            println 'column full'

            board.board.withIndex().any {col, index ->
                if (col.size()+1 <= 3) {
                    println '-->placing dice: ' + board.board
                    return board.addNumber(index, dice)             //return true to continue next condition
                } else {
                    println '!!!No columns available!!!'
                    return false                                    //return false to keep searching until ends on false
                }
            }
        } else {
            println '-->placing dice in ' + board.board
            return true
        }
    }
}
