package Graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;

public class Graph {

	boolean[][] vertices;
	
	// Input: pixel array, luminosity values
	public Graph(double[][] pixels, double threshold) {
		// create new graph with all vertices initialized as false
		this.vertices = new boolean[pixels.length][pixels[0].length];
		
		// if position in pixels is below threshold, add it as true
		for (int i = 0; i < pixels.length; ++i) {
			for (int j = 0; j < pixels[0].length; ++j) {
				if (pixels[i][j] < threshold) {
					this.vertices[i][j] = true;
				}
			}
		}
	}
	
	// Input: vertices array, boolean values
	public Graph(boolean[][] vertices) {
		this.vertices = vertices;
	}
	
	// determine connected components in the graph
	// if two vertices are connected by black (any adjacent or corner point
	// in image), there is an edge between those two points
	public ArrayList<ArrayList<Vertex>> connectedComponents() {
		ArrayList<ArrayList<Vertex>> components = new ArrayList<ArrayList<Vertex>>();

		boolean[][] visited = new boolean[vertices.length][vertices[0].length];

		// iterate over all vertices
		for (int i = 0; i < vertices.length; ++i) {
			for (int j = 0; j < vertices[0].length; ++j) {

				// if the vertex is unvisited, it's a new component
				if (vertices[i][j] && !visited[i][j]) {
					components.add(new ArrayList<Vertex>());

					// visit all vertices in component and mark them as visited
					Deque<Vertex> stack = new ArrayDeque<Vertex>();
					stack.push(new Vertex(i, j));

					while (!stack.isEmpty()) {
						// pop the top of the stack and mark it visited and add
						// to component
						Vertex v = stack.peekFirst();
						int x = v.getX();
						int y = v.getY();

						stack.removeFirst();
						visited[x][y] = true;
						components.get(components.size() - 1).add(v);

						// add all adjacent vertices to stack
						// up
						if (y > 0 && vertices[x][y - 1]) {
							if (!visited[x][y - 1]) {
								stack.addFirst(new Vertex(x, y - 1));
							}
						}
						// down
						if (y < vertices[0].length - 1 && vertices[x][y + 1]) {
							if (!visited[x][y + 1]) {
								stack.addFirst(new Vertex(x, y + 1));
							}
						}
						// left
						if (x > 0) {
							if (vertices[x - 1][y]) {
								if (!visited[x - 1][y]) {
									stack.addFirst(new Vertex(x - 1, y));
								}
							}
							// up left
							if (y > 0 && vertices[x - 1][y - 1]) {
								if (!visited[x - 1][y - 1]) {
									stack.addFirst(new Vertex(x - 1, y - 1));
								}
							}
							// down left
							if (y < vertices[0].length && vertices[x - 1][y + 1]) {
								if (!visited[x - 1][y + 1]) {
									stack.addFirst(new Vertex(x - 1, y + 1));
								}
							}
						}
						// right
						if (x < vertices.length - 1) {
							if (vertices[x + 1][y]) {
								if (!visited[x + 1][y]) {
									stack.addFirst(new Vertex(x + 1, y));
								}
							}
							// up right
							if (y > 0 && vertices[x + 1][y - 1]) {
								if (!visited[x + 1][y - 1]) {
									stack.addFirst(new Vertex(x + 1, y - 1));
								}
							}
							// down right
							if (y < vertices[0].length && vertices[x + 1][y + 1]) {
								if (!visited[x + 1][y + 1]) {
									stack.addFirst(new Vertex(x + 1, y + 1));
								}
							}
						}
					}
				}
			}
		}
		
		return components;
	}

	// Static method that takes an array list of vertices and returns the vertices
	// of the bounding box of those vertices
	public ArrayList<ArrayList<Integer>> boundingBox(ArrayList<ArrayList<Vertex>> components, int xBound, int yBound) {
		
		ArrayList<ArrayList<Integer>> boxes = new ArrayList<>();
		
		for (int i = 0; i < components.size(); ++i) {
			
			ArrayList<Vertex> currentComponent = components.get(i);
			
			int minX = xBound, maxX = 0,
				minY = yBound, maxY = 0;
			
			for (Vertex v : currentComponent) {
				int x = v.getX(), y = v.getY();
	
				// determine if point is more extreme than previous
				if (x > maxX) maxX = x;
				if (x < minX) minX = x;
				if (y > maxY) maxY = y;
				if (y < minY) minY = y;
			}
			/*
			// try extending the bounding box by a certain amount on all sides
			// if another component falls entirely within the new box, add that to the current component
			int newMinX = (minX - 0.2*xBound) > 0 ? minX - (int)(0.2*xBound) : 0;
			int newMaxX = (maxX + 0.2*xBound) < xBound ? maxX + (int)(0.2*xBound) : xBound;
			int newMinY = (minY - 20) > 0 ? (minY - 20) : 0;
			int newMaxY = (maxY + 20) < yBound ? (maxY + 20) : yBound;
			
			for (int j = 0; j < components.size(); ++j) {
				ArrayList<Vertex> otherComponent = components.get(j);
				// check only other components
				if (!currentComponent.equals(otherComponent)) {
					
					boolean inside = true;
					// check to see if all vertices of other component fall in component
					for (Vertex v : otherComponent) {
						if (v.getX() > newMaxX || v.getX() < newMinX || 
							v.getY() > newMaxY || v.getY() < newMinY) {
							inside = false;
							break;
						}
					}
					if (inside) {
						// add the other component to the current one
						currentComponent.addAll(otherComponent);
						components.remove(otherComponent);
						// recreate the bounding box
						for (Vertex v : currentComponent) {
							int x = v.getX(), y = v.getY();
				
							// determine if point is more extreme than previous
							if (x > maxX) maxX = x;
							if (x < minX) minX = x;
							if (y > maxY) maxY = y;
							if (y < minY) minY = y;
						}
					}
				}
			}
			*/
			//boxes.add(new ArrayList<>(Arrays.asList(newMaxX, newMinX, newMaxY, newMinY)));
			boxes.add(new ArrayList<>(Arrays.asList(maxX, minX, maxY, minY)));
		}
		return boxes;
	}
	
}
