import java.awt.Color;
/**
 * Square represents a pixel to draw in  the view and a node in the TopoMap
 * 
 * @author Sean Martens
 * @since 6/5/2020
 *
 */
public class Square{
	
	public final int x, y, elevation;
	private Color color;
	
	/**
	 * All initial args are final for outside class usability.
	 * Color is green with it's shade derived from the elevation.
	 * 
	 * @param x
	 * @param y
	 * @param elevation
	 */
	public Square(int x, int y, int elevation) {
		super();
		
		this.x = x;
		this.y = y;
		
		//Elevation must be 0 - 255.
		if(elevation > 255) {
			elevation = 255;
		}else if(elevation < 0) {
			elevation = 0;
		}
		this.elevation = elevation;
		
		//Set shade of green for elevation.
		color = new Color(0,elevation,0);
	}
	
	/**
	 * Return the current set color for this square.
	 * @return
	 */
	public Color getColor(){
		return color;
	}
	
	/**
	 * Overwrites the stored color for this square.
	 * Has no effect on elevation.
	 * @param c
	 */
	public void setColor(Color c) {
		this.color = c;
	}
	
	/**
	 * Returns direct line distance from this square to the other using distance formula.
	 * Elevation is accounted for in distance.
	 * @param other
	 * @return
	 */
	public double distanceTo(Square other) {
		double distance = Math.sqrt((other.y - y) * (other.y - y) + (other.x - x) * (other.x - x));; //Lateral distance to other square.
		distance += Math.abs(this.elevation - other.elevation); //vertical distance 
		return  distance;
	}
	
	/**
	 * Set color back to elevation representation color.
	 */
	public void resetColor() {
		color = new Color(0,elevation,0);
	}
	
	/**
	 * Creates a hashcode for use in HashSets.
	 */
	@Override
	public int hashCode() {
		return (x * -1000) + y;
	}
	
	/**
	 * Two squares are equal if they have the same X and Y coordinate 
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof Square) {
			Square otherSquare = (Square) obj;
			return otherSquare.x == this.x && otherSquare.y == this.y;
		}
		return false;
	}
}
