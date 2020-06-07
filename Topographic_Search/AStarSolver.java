import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * A* solver to return optimal path through the Topology Map
 * @author Sean Martens
 * @since 6/5/2020
 *
 */
public class AStarSolver implements Solver {
	
	private Node start;
	private PriorityQueue<Node> frontier;
	private HashSet<Square> visited = new HashSet<Square>();
	
	/**
	 * returns found path using A*
	 */
	@Override
	public Path getPath(TopoMap topo) {
		//Hold the end square
		Square goalSquare = topo.getSquare(topo.width - 1, topo.height -1);
		//Set start node
		start = new Node(topo.getSquare(0, 0), 0, null, goalSquare);
		
		//Create frontier
		frontier = new PriorityQueue<Node>();
		
		//Add start node to frontier
		frontier.add(start);
		
		while(!frontier.isEmpty()) {
			//Get current node
			Node curNode = frontier.poll();
			visited.add(curNode.square);
			//Check for goal state
			if(curNode.square.equals(goalSquare)) {
				//Goal found
				Path p = new Path();
				//Add to steps to path (this is from goal to start)
				while(curNode != null) {
					p.addStep(curNode.square);
					curNode = curNode.parent;
				}
				//Make the path from start to goal
				p.flipPath();
				return p;
			}
			//Generate children
			for(Square s : topo.getNeighbors(curNode.square.x, curNode.square.y)) {
				Node n = new Node(s, curNode.g + curNode.square.distanceTo(s), curNode, goalSquare);
				if(!visited.contains(n.square)){
					//This square has not been expanded
					frontier.add(n);
				}
			}
		}
		return null;
	}
	
	/**
	 * Inner class to represent Nodes in the A* frontier.
	 * @author Sean Martens
	 *
	 */
	private class Node implements Comparable<Node>{
		public final Square square;
		public final Node parent;
		public final double f, g, h;

		public Node(Square square, double g, Node parent, Square end) {
			this.square = square;
			this.parent = parent;
			
			this.g = g; //Distance from node and start node
			this.h = square.distanceTo(end); //Heuristic from node to end node
			this.f = g + h; //Cost of node. f = g + h
		}
		
		/**
		 * Compare to uses current distance + heuristic to place Node into a priority queue. 
		 */
		@Override
		public int compareTo(Node n) {
			return (int) (f - n.f);
		}
	}
}
