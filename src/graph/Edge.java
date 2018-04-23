package graph;

public class Edge {
	private static final int DEFAULT_WEIGHT = 1;
	private static int id = 1;

	private Node source, target;
	private int weight;
	private int edgeID;

	public Edge(Node n1, Node n2) {

		this(n1, n2, DEFAULT_WEIGHT);
		id++;

	}

	public Edge(Node n1, Node n2, int weight) {

		this.source = n1;
		this.target = n2;
		this.weight = weight;
		this.edgeID = id;

	}

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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Edge))
			return false;

		Edge _obj = (Edge) obj;
		return _obj.source.equals(source) && _obj.target.equals(target) && _obj.weight == weight;
	}

	@Override
	public int hashCode() {
		int result = source.hashCode();
		result = 31 * result + target.hashCode();
		result = 31 * result + weight;
		return result;
	}

	@Override
	public String toString() {
		String s = "{\"id\": \"e" + this.getEdgeID() + "\", " + "\"source\": \"n" + source.getID() + "\", "
				+ "\"target\": \"n" + target.getID() + "\"}";
		return s;
	}

}
