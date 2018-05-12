package Graph;

public class Vertex {
	int x;
	int y;
	public boolean visited;

	public Vertex(int x, int y) {
		this.x = x;
		this.y = y;
		this.visited = false;
	}

	public int getX() {	return x; }
	public int getY() { return y; }
}
