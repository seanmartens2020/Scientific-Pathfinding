/**
 * Class Main is the driver that creates a Topology Map(TopoMap) and passes it to a view.
 * 
 * @author Sean Martens
 * @since 6/5/2020
 *
 */
public class Main {
	public static void main(String [] args) {
		new Main();
	}
	
	private Main(){
		TopoMap topo = new TopoMap(1050, 750);
		new View(topo);
	}
}
