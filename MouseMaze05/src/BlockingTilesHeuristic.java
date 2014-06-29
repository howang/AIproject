

public class BlockingTilesHeuristic extends heuristic {

    /**
     * Guesses distance to goal based on the number of tiles between the mouse
     * and the exit
     */
    @Override
    public int score(char[][] board) {
        int numBlocking = 0;
        int mouse = getMousePosition_X(board);
        for (int i = mouse + 1; i < 6; i++) {
            if (board[2][i] != ' ') {
                numBlocking++;
            }
        }
        return numBlocking;
    }
}

