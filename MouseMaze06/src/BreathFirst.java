
import java.util.LinkedList;
import java.util.Queue;

public class BreathFirst extends Method {
    Queue<Grid> queue;
    int shady = 0;
    public BreathFirst(Grid start) {
        queue = new LinkedList<>();
        queue.add(start);
    }
    @Override
    public void perform() {
        long start_time = System.currentTimeMillis();
        System.out.println("Starting the BFS");
        if (queue.size() == 0) {
            System.out.println("Cant start with no Board");
            return;
        }
        Grid temp = queue.remove();
        temp.lastGrid= null;
        possibleMoves(temp);
        while (queue.size() != 0) {
            System.out.println("size : " + queue.size());
            if (success(temp.grid)) {
                    temp.ShowProcess();
                    System.out.println("foolowing is temp");
                    temp.show();
                System.out.println("gameover");
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
    @Override
    public void addtoQueue(Grid board) {
        //this.explored_nodes++;
        this.queue.add(board);
    }
}