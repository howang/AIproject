
/**
 * A* method. 
*/
import java.util.*;

public class AStar extends Method {

    PriorityQueue<Grid> queue;
    int heuristic;
    public AStar(Grid start, int heuristic) {
        this.heuristic = heuristic;
        Comparator<Grid> comparator = new AStarDistance();
        queue = new PriorityQueue<>(1, comparator);
        queue.add(start);
    }
    public void perform() {
        System.out.println("Starting the Astar");
        long start_time = System.currentTimeMillis();
        if (queue.size() == 0) {
            System.out.println("Cant start with no Board");
            return;
        }
        Grid temp = queue.remove();
        temp.lastGrid = null;
        possibleMoves(temp);
        while (queue.size() != 0) {
            System.out.println("size : " + queue.size());
            if (success(temp.grid)) {
                    temp.ShowProcess();
                System.out.println("Game Over --Solution FOUND");
                System.out.println("Nodes Explored :" + this.gridPassed.size());
                //System.out.println("Nodes Explored- :" + this.explored_nodes);
                long total_time = System.currentTimeMillis() - start_time;
                System.out.println("Time Spent :" + total_time + " ms");
                return;
            }
            temp = queue.remove();
            possibleMoves(temp);
        }
        System.out.println("EMPTY QUEUE");
        long total_time = System.currentTimeMillis() - start_time;
        System.out.println("Time Spent :" + total_time + " ms");
        System.out.println("Nodes Explored :" + this.gridPassed.size());
        //System.out.println("Nodes Explored- :" + this.explored_nodes);
        if (success(temp.grid)) {
                temp.ShowProcess();
            System.out.println("Game Over --Solution FOUND");
        } else {
            System.out.println("Game Over --Solution NOT found");
        }
    }
    public void addtoQueue(Grid board) {
         BlockingTilesHeuristic BlockingTilesHeuristic = new BlockingTilesHeuristic();
         board.heuristic = BlockingTilesHeuristic.score(board.grid);
         board.cost = board.lastGrid.cost + board.heuristic;
         System.out.println("board.cost is" + board.cost);
        // this.explored_nodes++;
         this.queue.add(board);
    }
}