package graph;

public class Edge {
	// Default value for edge weight for a weighted graph
	private static final int DEFAULT_WEIGHT = 1;
	// Initial value for a edge
	private static int id = 1;

	// Two Node instance variables used for composition
	private Node source, target;
	// two integer instance variables used to store an edges weight and ID values
	private int weight;
	private int edgeID;

	// Edge constructor with parameters
	public Edge(Node n1, Node n2) {
		// create and edge with the values passed in
		this(n1, n2, DEFAULT_WEIGHT);
		// increment id value for the next edge created
		id++;

	}

	// Edge constructor with parameters that also takes
	// in the weight value of an edge
	// weight value can be used for a weighted graph
	public Edge(Node n1, Node n2, int weight) {

		this.source = n1;
		this.target = n2;
		this.weight = weight;
		this.edgeID = id;

	}

	// Getter and setter methods
	// Used to set and get the values from the instance variable
	// Used for encapsulation
	public int getId() {
		return id;
	}

	public Node getSource() {
		return source;
	}

	public void setSource(Node n1) {
		this.source = n1;
	}

	public Node getTarget() {
		return target;
	}

	public void setTarget(Node n2) {
		this.target = n2;
	}

	public int getEdgeID() {
		return edgeID;
	}

	public void setEdgeID(int edgeID) {
		this.edgeID = edgeID;
	}

	// Overridden class equals method used to compare two edges are equal
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Edge))
			return false;

		Edge _obj = (Edge) obj;
		return _obj.source.equals(source) && _obj.target.equals(target) && _obj.weight == weight;
	}

	// Overridden hashcode method to ensure when search for an item it can be found
	// because each objects gets a unique hashcode which is integer that is
	// constant, ensures that objects can be compared
	@Override
	public int hashCode() {
		// generating a unique hashcode for each object
		int result = source.hashCode();
		result = 31 * result + target.hashCode();
		result = 31 * result + weight;
		return result;
	}

	// Overridden toString method use to provide the 
	// content of an edge object in a string format.
	@Override
	public String toString() {
		String s = "{\"id\": \"e" + this.getEdgeID() + "\", " + "\"source\": \"n" + source.getID() + "\", "
				+ "\"target\": \"n" + target.getID() + "\"}";
		return s;
	}

}
