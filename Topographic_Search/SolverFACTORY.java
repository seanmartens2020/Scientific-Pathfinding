/**
 * Factory pattern to create solvers out of algorithm representation string.
 * More Solvers may be added to this Factory.
 * 
 * @author Sean Martens
 * @since 6/5/2020
 */
public class SolverFACTORY {
	public static Solver get(String solve) {
		switch(solve) {
		case "ASTAR":
			return new AStarSolver();
		case "GENETIC":
			return new GeneticSolver();
		default:
			return null;
		}
	}
}
