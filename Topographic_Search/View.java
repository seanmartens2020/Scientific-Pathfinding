import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

/**
 * A GUI class that shows the current Topology Map.
 * 
 * @author Sean Martens
 * @since 6/5/2020
 *
 */
public class View {
	
	private JFrame frame;
	private TopoMap topo;
	
	public View(TopoMap topo) {
		this.topo = topo;
		
		//Create window
		frame = new JFrame("Topography Map");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		drawTopo();
		
		//Create window menu
		JMenuBar menuBar = new JMenuBar();
		
		JMenu dropDownMenu = new JMenu("Menu");
		JMenuItem aStar = new JMenuItem("Solve with A*");
		JMenuItem genetic = new JMenuItem("Solve with Genetic Algorithm");
		
		JMenuItem reset = new JMenuItem("Reset");
		JMenuItem about = new JMenuItem("About");
		JMenuItem help = new JMenuItem("Help");
		
		dropDownMenu.add(aStar);
		dropDownMenu.add(genetic);
		dropDownMenu.add(reset);
		dropDownMenu.add(about);
		dropDownMenu.add(help);
		menuBar.add(dropDownMenu);
		
		frame.setJMenuBar(menuBar);
		
		
		aStar.addActionListener(event -> {solve("ASTAR");});
		genetic.addActionListener(e -> {solve("GENETIC");});
		reset.addActionListener(event -> reset());
		about.addActionListener(event -> JOptionPane.showMessageDialog(frame, "Algorithms will solve this path problem starting from the top left and ending at the bottom right."
				+ "\nLight green represents higher elevation and black represents lower. Each pixel has a small amount of randomness to make the hill cimbing problem more difficult."
				+ "\nRed represents the found path."
				+ "\nA* returns a 100% optimal solution, while the GA returns a machine learned estimate."));
		help.addActionListener(event -> JOptionPane.showMessageDialog(frame, "Clicking an option containing \"Solve with\" will draw a solution path."
				+ "\nThe size of the map can be edited in the source code, the GA will incur less of a slowdown on larger sets compared to A*."
				+ "\nThis application is a example of path finding scientific algorithms, the GUI freezes while computing because multi-threading was not a requirement for this quickly developed project."));
		
		frame.pack();
		frame.setVisible(true);
	}
	
	/**
	 * Takes the selected algorithm as a string, runs the solver, and draws the path on the GUI.
	 * @param algo
	 */
	private void solve(String algo) {
		frame.setTitle("Solving...");
		Solver solver = SolverFACTORY.get(algo);
		Path path;
		long start = System.currentTimeMillis();
		path = solver.getPath(topo);
		long end = System.currentTimeMillis();
		float sec = (end - start) / 1000F;
		
		frame.setTitle("Solved!");
		String message = "Search finished.\nDistance: " + (int) path.getDistance();
		message += "\nNumber of steps: " + path.getPath().size();
		message+= "\nPath found in " + sec + " seconds.";
		message += "\nPlease wait for image to render after clicking OK.";
		JOptionPane.showMessageDialog(frame, message);
		
		frame.setTitle("Rendering...");
		drawPath(path);
		drawTopo();
		frame.setTitle("Topography Map");
	}
	
	/**
	 * Draws each red path square on the TopoMap
	 * @param path
	 */
	private void drawPath(Path path) {
		for(Square s : path.getPath()) {
			topo.getSquare(s.x, s.y).setColor(Color.RED);;
		}
	}
	
	/**
	 * Takes the TopoMap and builds an ImageBuffer to place on the GUI.
	 * Drawing the GUI with thousands of controls is too slow, which is why the overhead for ImageBuffer is used. 
	 */
	private void drawTopo() {
		//Clear the frame.
		frame.getContentPane().removeAll();
		frame.repaint();
		//Create buffer for new image.
		JPanel buffer = new JPanel();
		buffer.setLayout(null);
		buffer.setBounds(0,0, topo.width, topo.height);
		
		for(int x = 0; x < topo.width; x++) {
			for(int y = 0; y < topo.height; y++) {
				JPanel jp = new JPanel();
				//jp.setBackground(topo.getPixel(x, y).getColor());
				jp.setBackground(topo.getSquare(x, y).getColor());
				jp.setBounds(x, y, 1, 1);
				buffer.add(jp);
			}
		}
		
		//Create buffered image to remove overhead of putting thousands of components on GUI.
		BufferedImage bi = new BufferedImage(topo.width, topo.height, BufferedImage.TYPE_INT_RGB);
	    Graphics2D g = bi.createGraphics();
	    buffer.paint(g);
	    ImageIcon ii = new ImageIcon(bi);
	    JLabel jl = new JLabel(ii);
	    jl.setBounds(0, 0, topo.width, topo.height);
		frame.add(jl);
	}
	
	/**
	 * Change every pixel back to their elevation color.
	 */
	private void reset() {
		for(int y = 0; y < topo.height; y++) {
			for(int x = 0; x < topo.width; x++) {
				topo.getSquare(x, y).resetColor();
			}
		}
		drawTopo();
	}
}
