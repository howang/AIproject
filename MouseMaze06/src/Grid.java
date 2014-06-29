/**
 * Construct the grid of game;
 */
import java.util.*;

public class Grid {
    public char[][] grid;
    public Grid lastGrid;
    public int cost; //cost to be used in the hurstic fucntion
    public int heuristic;
    public long time = System.currentTimeMillis();
    public int dimension=7;
    
    public Grid(){
    	grid=new char[dimension][dimension];
    }
    public Grid(String level) {
    	switch (level) {
        case "easy":
        	grid = new char[][]{
        			{3, 6, 7, 6, 7, 3},
                    {4, 0, 3, 0, 3, 5},
                    {1, 2, 4, 3, 5, 4},
                    {3, 3, 3, 4, 4, 3},
                    {5, 5, 5, 3, 3, 5},
                    {4, 4, 4, 4, 4, 4}};
    	break;
        case "normal":  
            grid = new char[][]{
                {' ', ' ', '<', '>', '<', '>',' '},
                {' ', '<', '>', '<', '>', '^',' '},
                {' ', ' ', 'B', 'A', '^', '|',' '},
                {'<', '>', '<', '>', 'v', 'v',' '},
                {'^', ' ', ' ', '^', '<', '>',' '},
                {'v', ' ', ' ', 'v', '<', '>',' '},
                {' ', ' ', ' ', ' ', ' ', ' ',' '}};
        break;
        case "hard":  
            grid = new char[][]{
            		{6, 8, 7, 3, 3, 3},
                    {3, 6, 7, 4, 5, 5},
                    {4, 0, 1, 2, 4, 4},
                    {6, 7, 3, 0, 0, 0},
                    {0, 3, 4, 0, 6, 7},
                    {0, 4, 6, 7, 6, 7}};      
    }
}
    public void show() {
        System.out.println();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();

    }
    public void copy(char[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            System.arraycopy(grid[i], 0, this.grid[i], 0, grid[i].length);
        }
    }
    public int currentLevel() {
        Grid currentGrid = this;
        int steps = 0;
        while (currentGrid.lastGrid != null) {
        	currentGrid = currentGrid.lastGrid;
            steps++;
        }
        return steps;
    }
    public void ShowProcess() {
        Grid currentGrid = this;
        System.out.println("********Final result*********");
        Stack<Grid> stack = new Stack<Grid>();
        while (currentGrid.lastGrid != null) {
            stack.add(currentGrid.lastGrid);
            currentGrid = currentGrid.lastGrid;
        }
        int steps = stack.size();
        while (!stack.empty()) {
            stack.pop().show();
        }
        System.out.println("------ Done in  " + steps + " steps");
    }
    /*
     * Move a specific tile in a specific direction and returns the new grid
     * after that move
     */
    char[][] move(char[][] grid, int i, int j, char move) {
        switch (move) {
            case '<':
                grid[i][j - 1] = grid[i][j];
                grid[i][j] = grid[i][j + 1];
                if (grid[i][j] == '-') {
                    grid[i][j + 1] = grid[i][j + 2];
                    grid[i][j + 2] = ' ';
                } else {
                    grid[i][j + 1] = ' ';
                }
                break;
            case '>':
                grid[i][j + 1] = grid[i][j];
                grid[i][j] = grid[i][j - 1];
                if (grid[i][j - 1] == '-') {
                    grid[i][j - 1] = grid[i][j - 2];
                    grid[i][j - 2] = ' ';
                } else {
                    grid[i][j - 1] = ' ';
                }
                break;
            case '^':
                grid[i - 1][j] = grid[i][j];
                grid[i][j] = grid[i + 1][j];
                if (grid[i + 1][j] == '|') {
                    grid[i + 1][j] = grid[i + 2][j];
                    grid[i + 2][j] = ' ';
                } else {
                    grid[i + 1][j] = ' ';
                }
                break;
            case 'v':
                grid[i + 1][j] = grid[i][j];
                grid[i][j] = grid[i - 1][j];
                if (grid[i - 1][j] == '|') {
                    grid[i - 1][j] = grid[i - 2][j];
                    grid[i - 2][j] = ' ';
                } else {
                    grid[i - 1][j] = ' ';
                }
                break;
        }
        return grid;
    }
    /*
     * Makes sure that the no explored state can be repeated , returns true in
     * case that the grid configuration is unqiueq false otherwise
     */
    public boolean canAdd(char[][] grid, Queue<Grid> explored) {
        if (explored.isEmpty()) {
            return true;
        }
        for (Grid temp : explored) {
            boolean found = Arrays.deepEquals(grid, temp.grid);
            if (found) {
                return false;
            }
        }
        return true;
    }
}