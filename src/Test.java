/**
 * Test the efficiency of different methods to reach the final goal
 */
public class Test {
    public static void main(String[] args) {
    	String method="AStar";//DepthFirst,IterativeDepthFirst,BreathFirst,Greed,AStar
    	String level="normal";//easy,normal,hard
    	Grid grid=new Grid(level);
    	grid.show();
        System.out.println("l:left; r:right; u:up; d:down; h:head; t:tail");
        System.out.println("rules:left and right move together, up and down move together, head and tail move together");
        System.out.println("once the head is move to position[2][5], it wins!");
    	if (method.equalsIgnoreCase("DepthFirst")) {
    		DepthFirst method2 = new DepthFirst(grid);
            method2.perform();
        }
    	else if (method.equalsIgnoreCase("IterativeDepthFirst")) {
    		IterativeDepthFirst method2 = new IterativeDepthFirst(grid);
            method2.perform();
        }
        else if (method.equalsIgnoreCase("BreathFirst")) {
        	BreathFirst method2 = new BreathFirst(grid);
            method2.perform();
        }
        else if (method.equalsIgnoreCase("Greedy")) {
            int heuristic = Character.getNumericValue(method.charAt(1));
            //System.out.println("heuristic is "+heuristic);
            Greedy method2 = new Greedy(grid, heuristic);
            method2.perform();
        }
        else if (method.equalsIgnoreCase("AStar")) {
            int heuristic = Character.getNumericValue(method.charAt(1));  
            AStar method2 = new AStar(grid, heuristic);
            method2.perform();
        }
}
}