package graph;

import java.util.Random;

// Node class used to emulate a node on a graph 
// and to store information about the node
public class Node {

	// Private instance variables one integer id and one boolean visited
	// they are private to adhere to encapsulation
	private int id;
	private boolean visited;

	// Node constructor variable called when a node object is created
	// takes in an integer value which is used as the node id
	public Node(int id) {
		// Initialize the values
		super();
		this.id = id;
		this.visited = false;
	}

	// Getter and setter methods
	// Used to set and get the values from the instance variable
	// Used for encapsulation
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

	// getX and getY methods used to generate a
	// random x and y value to plot the node
	public int getX() {
		Random gen = new Random();
		return gen.nextInt(3);
	}

	public int getY() {
		Random gen = new Random();
		return gen.nextInt(3);
	}

	// Overridden class equals method used to compare two nodes are equal
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Node))
			return false;

		Node _obj = (Node) obj;
		return _obj.id == id;
	}

	// Overridden hashcode method to ensure when search for an item it can be found
	// because each objects gets a unique hashcode which is integer that is
	// constant, ensures that objects can be compared
	@Override
	public int hashCode() {
		return id;
	}

	// Overridden toString method use to provide the
	// content of an node object in a string format.
	@Override
	public String toString() {
		String s = "{\"id\": \"n" + getID() + "\", " + "\"label\": \"node" + getID() + "\", " + "\"x\": \"" + getX()
				+ "\"," + "\"y\": \"" + getY() + "\", " + "\"size\": \"" + 3 + "\"}";
		return s;
	}

}
