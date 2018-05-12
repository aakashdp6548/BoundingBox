package main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import Graph.Graph;
import Graph.Vertex;
import input.ImageToArray;

public class Main {

	public static void main(String[] args) {
		// load the image as an array of pixels
		File file = new File("Resources/integral.png");
		double[][] pixels = ImageToArray.convertImage(file);

		Graph graph = new Graph(pixels, 1.0);
		ArrayList<ArrayList<Vertex>> components = graph.connectedComponents();
		ArrayList<ArrayList<Integer>> boxes = graph.boundingBox(components, pixels.length, pixels[0].length);
		
		// draw image on JFrame and display on screen
		BufferedImage img;
		try {
			img = ImageIO.read(file);
			JFrame frame = new JFrame();
			frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			frame.setSize(img.getWidth(), img.getHeight() + 50);
			frame.setVisible(true);
			
			JPanel panel = new JPanel() {
				protected void paintComponent(Graphics g) {
					super.paintComponent(g);
					g.drawImage(img, 0, 0, null);
					
					// draw all bounding boxes
					for (ArrayList<Integer> boundingBox : boxes) {
						int maxX = boundingBox.get(0);
						int minX = boundingBox.get(1);
						int maxY = boundingBox.get(2);
						int minY = boundingBox.get(3);
						
						// draw bounding box on image
						g.drawRect(minY, minX, maxY - minY, maxX - minX);
					}
				}
			};
			
			frame.add(panel);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
