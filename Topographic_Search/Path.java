import java.util.HashSet;
import java.util.LinkedList;

/**
 * Path represents an ordered set of squares taken.
 * 
 * @author Sean Martens
 * @since 6/5/2020
 */
public class Path implements Comparable<Path>{
	private LinkedList<Square> steps = new LinkedList<Square>();
	private HashSet<Square> hashedSteps = new HashSet<Square>();
	private double distance = 0.0;
	
	/**
	 * Add step to the current path.
	 * @param square
	 */
	public void addStep(Square square) {
		//Make sure this  is a unique step.
		if(hashedSteps.add(square)) {
			if(steps.size() > 0) {
				distance += steps.getLast().distanceTo(square);
			}
			steps.add(square);
		}
	}
	
	/**
	 * Reverses the order of the path.
	 * Useful for A* where the path is built recursively from end to start.
	 */
	public void flipPath() {
		LinkedList<Square> tmp = new LinkedList<Square>();
		
		while(steps.size() > 0) {
			tmp.add(steps.pollLast());
		}
		
		steps = tmp;
	}
	
	/**
	 * Distance of the current path.
	 * @return
	 */
	public double getDistance() {
		return distance;
	}
	
	/**
	 * Returns the list of squares taken in order they were added (Unless flipped)
	 * @return
	 */
	public LinkedList<Square> getPath() {
		return steps;
	}
	
	/**
	 * Returns "(x,y)\n"
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Square s : steps) {
			sb.append("(");
			sb.append(s.x);
			sb.append(", ");
			sb.append(s.y);
			sb.append(") ");
			sb.append("\n");
		}
		return sb.toString();
	}

	/**
	 * Compares paths based on current length.
	 */
	@Override
	public int compareTo(Path p) {
		return (int) (this.getDistance() - p.getDistance());
	}
}
