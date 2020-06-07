/**
 * Interface to guarantee Solvers will return a path using getPath(TopoMap);
 * 
 * @author Sean Martens
 * @since 6/5/2020
 *
 */
public interface Solver {
	public Path getPath(TopoMap topo);
}