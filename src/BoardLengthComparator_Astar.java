
import java.util.Comparator;

public class BoardLengthComparator_Astar implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {

        Grid b1 = (Grid) o1;
        Grid b2 = (Grid) o2;
        int j1 = ((Integer) b1.heuristic + b1.cost);
        int j2 = ((Integer) b2.heuristic + b2.cost);
        return (j1 < j2 ? -1 : ((j1 == j2 && b1.time<b2.time) ? 0 : 1));

    }
}