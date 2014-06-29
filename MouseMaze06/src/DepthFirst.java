
import java.util.Stack;

public class DepthFirst extends Method {

    Stack<Grid> stack;

    public DepthFirst(Grid start) {
        stack = new Stack<>();
        stack.add(start);
    }
    @Override
    public void perform() {
        System.out.println("Starting the DFS");
        long start_time = System.currentTimeMillis();

        if (stack.size() == 0) {
            System.out.println("Cant start with no Board");
            return;
        }
        Grid temp = stack.pop();
        temp.lastGrid = null;
        possibleMoves(temp);//add to stack
        while (stack.size() != 0) {
            System.out.println("size : " + stack.size());
            if (success(temp.grid)) {
                    temp.ShowProcess();;
                    temp.show();;
                System.out.println("gameover");
                System.out.println("Nodes Explored :" + this.gridPassed.size());
                //System.out.println("Nodes Explored- :" + this.explored_nodes);

                long total_time = System.currentTimeMillis() - start_time;
                System.out.println("Time Spent :" + total_time + " ms");
                return;
            }
            temp = stack.pop();
            possibleMoves(temp);
        }
        //stack is empty
        System.out.println("EMPTY stack");
        long total_time = System.currentTimeMillis() - start_time;
        System.out.println("Time Spent :" + total_time + " ms");
//        temp.visualize();
        System.out.println("Nodes Explored :" + this.gridPassed.size());
        //System.out.println("Nodes Explored- :" + this.explored_nodes);

        if (success(temp.grid)) {
                temp.ShowProcess();;
            System.out.println("Game Over --Solution FOUND");
        } else {
            System.out.println("Game Over --Solution NOT found");
        }
    }
    @Override
    public void addtoQueue(Grid board) {
        //this.explored_nodes++;
        this.stack.add(board);
    }
}