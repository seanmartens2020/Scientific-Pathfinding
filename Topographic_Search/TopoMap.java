import java.util.LinkedList;
/**
 * TopoMap represents the puzzle's topology being solved.
 * The start will be defaulted to (0,0) and the end will be (width - 1, height - 1)
 * @author Sean Martens
 * @since 6/5/2020
 *
 */
public class TopoMap {
	
	public final int width, height;
	private Square[][] squares;
	
	public TopoMap(int width, int height) {
		this.width = width;
		this.height = height;
		
		squares = new Square[height][width];
		
		for(int h = 0; h < height; h++) {
			for(int w = 0; w < width; w++) {
				//Create squares on the topology
				int elevation = (int) (Math.cos(w / 100) * 255/2);
				elevation += (int) (Math.cos(h / 100) * 255/2);
				elevation = Math.abs(elevation);
				//Add slight randomness to each pixel 
				elevation += (int) (Math.random() * 10);
				
				//Draw black grid lines 3 pixels wide
				if(w % 100 > 97 || h % 100 > 97) {
					elevation = (int)(Math.random() * 10);
				}
				
				squares[h][w] = new Square(w, h, elevation);//elevation);
			}
		}
	}
	
	/**
	 * Returns the square at (x,y) in the TopoMap.
	 * Throws index out of bounds error.
	 * 
	 * @param x
	 * @param y
	 * @return Square
	 */
	public Square getSquare(int x, int y) {
		return squares[y][x];
	}
	
	/**
	 * Returns a List of all neighboring squares.
	 * Squares are neighboring if they are veritcally or horizontally adjacent. 
	 * @param x
	 * @param y
	 * @return
	 */
	public LinkedList<Square> getNeighbors(int x, int y) {
		LinkedList<Square> neighbors = new LinkedList<Square>();
		
		//up
		if(y > 0) {
			neighbors.add(getSquare(x, y-1));
		}
		//down
		if(y < height - 1) {
			neighbors.add(getSquare(x, y+1));
		}
		//left
		if(x>0) {
			neighbors.add(getSquare(x-1, y));
		}
		
		//right
		if(x < width - 1) {
			neighbors.add(getSquare(x+1, y));
		}
		return neighbors;
	}
}
