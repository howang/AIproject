import java.util.Comparator;


public class Distance implements Comparator {
	 @Override
	 public int compare(Object o1, Object o2) {

	     Grid b1 = (Grid) o1;
	     Grid b2 = (Grid) o2;
	     int j1 = ((Integer) b1.heuristic);
	     int j2 = ((Integer) b2.heuristic);	        
	     return (j1 < j2 ? -1 : ((j1 == j2 && b1.time < b2.time) ? 0 : 1));
	 }
}
