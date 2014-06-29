import java.util.*;
/**
 * Explain detailed the moving strategy.
 */
public class Method{

    Queue<Grid> explored;
    int explored_nodes = 0;

    public void clearExplored() {
        this.explored = new LinkedList<Grid>();
    }

    public Method() {
        explored = new LinkedList<>();
    }

    public void perform() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addtoQueue(Grid board) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void possibleMoves(Grid b) {
        for (int i = 0; i < b.grid.length; i++) {
            for (int j = 0; j < b.grid[i].length; j++) {
                Grid tempBoard = new Grid();
                tempBoard.copy(b.grid);
                //end of TempBoard
                switch (b.grid[i][j]) {
                    case 'B': //tail
                    case '<':
                        try {
                            if (b.grid[i][j - 1] == ' ') {
                                tempBoard.grid = tempBoard.move(tempBoard.grid, i, j, '<');
                                if (tempBoard.canAdd(tempBoard.grid, this.explored)) {
                                    tempBoard.lastGrid = b;
                                    addtoQueue(tempBoard);
                                    explored.add(tempBoard);
                                }
                            }
                        } catch (Exception e) {
                        }

                        break;
                    case 'A'://head
                    case '>':
                        try {
                            if (b.grid[i][j + 1] == ' ') {
                                tempBoard.grid = tempBoard.move(tempBoard.grid, i, j, '>');
                                if (tempBoard.canAdd(tempBoard.grid, this.explored)) {
                                    tempBoard.lastGrid = b;
                                    addtoQueue(tempBoard);
                                    explored.add(tempBoard);
                                }
                            }
                        } catch (Exception e) {
                        }

                        break;
                    case '^':
                        try {
                            if (b.grid[i - 1][j] == ' ') {
                                tempBoard.grid = tempBoard.move(tempBoard.grid, i, j, '^');
                                if (tempBoard.canAdd(tempBoard.grid, this.explored)) {
                                    tempBoard.lastGrid = b;
                                    addtoQueue(tempBoard);
                                    explored.add(tempBoard);
                                }
                            }
                        } catch (Exception e) {
                        }

                        break;
                    case 'v':
                        try {
                            if (b.grid[i + 1][j] == ' ') {
                                tempBoard.grid = tempBoard.move(tempBoard.grid, i, j, 'v');
                                if (tempBoard.canAdd(tempBoard.grid, this.explored)) {
                                    tempBoard.lastGrid = b;
                                    addtoQueue(tempBoard);
                                    explored.add(tempBoard);
                                }
                            }
                        } catch (Exception e) {
                        }

                        break;

                }
            }
        }
    }

    public boolean isGoal(char[][] board) {
        if (board[2][6] == 'A') {
        	System.out.println("25 is "+board[2][5]);
            return true;
        }
        return false;
    }
}