import java.util.Arrays;
import java.util.LinkedList;

/**
 * Genetic algorithm that uses mutations to create solutions.
 * @author Sean Martens
 * @since 6/5/2020
 *
 */
public class GeneticSolver implements Solver{

	@Override
	public Path getPath(TopoMap topo) {
		
		LinkedList<Path> population = new LinkedList<Path>();
		population.add(stepPath(topo));
		
		for(int i = 0; i < 12; i++) {
			population.add(stepPath(topo));
		}
		
		for(int genNumber = 0; genNumber < 200; genNumber++) {
			LinkedList<Path> mutants = new LinkedList<Path>();
			for(Path p : population) {
				Path mutant = mutate(p, topo);
				mutants.add(mutant);
			}
			population.addAll(mutants);
			
			Path[] paths = population.toArray(new Path[population.size()]);
			Arrays.sort(paths);
			
			//Sorted in ASC order
			population = new LinkedList<Path>();
			for(int i = 0; i <= paths.length / 2; i++) {
				population.add(paths[i]);
			}
		}
		
		return population.getFirst();
	}

	/**
	 * Takes two points of the path and joins them with a 90 degree angle.
	 * @param path
	 * @param topo
	 * @return
	 */
	private Path mutate(Path path, TopoMap topo) {
		Path mutPath = new Path();
		
		Square[] squares = path.getPath().toArray(new Square[path.getPath().size()]);
		
		//Find gap to remove from path.
		if(squares.length < 101) {
			//This data set is too small to properly mutate. 
			return path;
		}
		int gapStart = (int) (Math.random() * (squares.length - 100) ) + 1;
		int gapEnd = (int) (Math.random() * 99);
		gapEnd += gapStart;
		
		//Add first part of path
		for(int i = 0; i < gapStart; i++) {
			mutPath.addStep(squares[i]);
		}
		
		//Get vertical direction
		int direction;
		if(squares[gapStart].y < squares[gapEnd].y) {
			direction = 1;
		}else {
			direction = -1;
		}
		int distance = Math.abs(squares[gapStart].y - squares[gapEnd].y);
		for(int i = 0; i < distance; i++) {
			mutPath.addStep(topo.getSquare(squares[gapStart].x, squares[gapStart].y + (i * direction)));
		}
		
		//Get horizontal direction
		if(squares[gapStart].x < squares[gapEnd].x) {
			direction = 1;
		}else {
			direction = -1;
		}
		distance = Math.abs(squares[gapStart].x - squares[gapEnd].x);
		for(int i = 0; i < distance; i++) {
			mutPath.addStep(topo.getSquare(squares[gapStart].x + (i * direction), squares[gapEnd].y));
		}
		
		//Add end of path
		for(int i = gapEnd; i < squares.length; i++){
			mutPath.addStep(squares[i]);
		}
		
		return mutPath;
	}
	
	/**
	 * Builds a path by using a random slope.
	 * @param topo
	 * @return
	 */
	private Path stepPath(TopoMap topo) {
		Path path = new Path();
		
		double slopeBias = Math.random();
		slopeBias = slopeBias / 2.0; //prefer a smaller bias because the mutation pushes the path downward
		
		Square goalSquare = topo.getSquare(topo.width - 1, topo.height - 1);
		Square curSquare = topo.getSquare(0,0);
		
		while(!curSquare.equals(goalSquare)) {
			if(Math.random() > slopeBias) {
				if(curSquare.x + 1 < topo.width) {
					curSquare = topo.getSquare(curSquare.x + 1, curSquare.y);
					path.addStep(curSquare);
				}
			}else {
				if(curSquare.y + 1 < topo.height) {
					curSquare = topo.getSquare(curSquare.x, curSquare.y + 1);
					path.addStep(curSquare);
				}
			}
		}
		return path;
	}	
}
