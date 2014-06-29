

import java.util.LinkedList;
import java.util.Queue;

public class IterativeDepthFirst extends Method {

    Queue<Grid> queue;
    //int depth = Integer.MAX_VALUE;
    int depth = 100;
    Grid start;

    public IterativeDepthFirst(Grid start) {
        queue = new LinkedList<>();
        this.start = start;
        queue.add(start);
    }
    @Override
    public void perform() {
        System.out.println("Starting the IDFS");
        long start_time = System.currentTimeMillis();
        if (queue.isEmpty()) {
            System.out.println("Cant start with no Board");
            return;
        }
        Grid temp = queue.remove();
        temp.lastGrid = null;
        possibleMoves(temp);
        int current_level = temp.currentLevel();
        while (!queue.isEmpty()) {
            try {
                possibleMoves(temp);
                System.out.println("Current Level " + current_level);
                if (current_level > this.depth) {
                    System.out.println("Queue is Empty");
                    break;
                }
                if (success(temp.grid)) {
                        temp.ShowProcess();
                        temp.show();
                    System.out.println("gameover");
                    System.out.println("Queue Size :" + this.queue.size());
                //System.out.println("Nodes Explored :" + this.numGrid);
                    System.out.println("Current Level " + current_level);
                    long total_time = System.currentTimeMillis() - start_time;
                    System.out.println("Time Spent :" + total_time + " ms");
                    return;
                }
                temp = this.queue.remove();
                current_level = temp.currentLevel();
            } catch (Exception e) {
                System.out.println("Game Over --Solution NOT found");
                System.out.println("Queue Size :" + this.queue.size());
                //System.out.println("Nodes Explored :" + this.numGrid);

                long total_time = System.currentTimeMillis() - start_time;
                System.out.println("Time Spent :" + total_time + " ms");
            } catch (OutOfMemoryError e) {
                System.out.println("Game Over --Solution NOT found");
                System.out.println("Queue Size :" + this.queue.size());
                //System.out.println("Nodes Explored :" + this.numGrid);

                long total_time = System.currentTimeMillis() - start_time;
                System.out.println("Time Spent :" + total_time + " ms");
                return;
            }
        }
        System.out.println("EMPTY QUEUE");
        long total_time = System.currentTimeMillis() - start_time;
        System.out.println("Time Spent :" + total_time + " ms");
        //System.out.println("Nodes Explored :" + this.numGrid);
        if (success(temp.grid)) {
                temp.ShowProcess();
            System.out.println("Game Over --Solution FOUND");
        } else {
            System.out.println("Game Over --Solution NOT found");
        }
    }
    @Override
    public void addtoQueue(Grid board) {
        //this.numGrid++;
        this.queue.add(board);
    }

}
