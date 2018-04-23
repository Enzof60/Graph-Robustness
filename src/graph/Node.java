package graph;

import java.util.Random;

public class Node {

	private int id;
	private boolean visited;

	public Node(int id) {
		super();
		this.id = id;
		this.visited = false;
	}

	public void setID(int id) {
		this.id = id;
	}

	public int getID() {
		return id;
	}

	public void setVisited(boolean b) {
		this.visited = b;
	}

	public boolean isVisited() {
		return visited;
	}

	public int getX() {
		Random gen = new Random();
		return gen.nextInt(3);
	}

	public int getY() {
		Random gen = new Random();
		return gen.nextInt(3);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Node))
			return false;

		Node _obj = (Node) obj;
		return _obj.id == id;
	}

	@Override
	public int hashCode() {
		return id;
	}
	@Override
	public String toString() {
		String s = "{\"id\": \"n" + getID() + "\", " + "\"label\": \"node" + getID() + "\", " + "\"x\": \"" + getX()
				+ "\"," + "\"y\": \"" + getY() + "\", " + "\"size\": \"" + 3 + "\"}";
		return s;
	}

}
