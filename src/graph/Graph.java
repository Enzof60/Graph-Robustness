package graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

// Graph class used to hold both node and edge object 
// with the use of the composite design pattern
public class Graph {
	// private instance variables to adhere to encapsulation

	// Creating a Set of type Node and Edge
	// Used to store nodes and edges objects
	// Using a Set ensures there are no within each set duplicates
	private Set<Node> nodes;
	private Set<Edge> edges;
	// Creating a HashMap with Node objects as the key and the pair values are a Set
	// of type Edge objects
	private Map<Node, Set<Edge>> graphHashMap;

	// instance variable seed which store the time
	// in millisecond when a graph object is created
	private long seed = System.currentTimeMillis();

	// Random is java class this is found in util java library
	// this object then uses seed to generate a random value used in the binomial
	// distribution phase
	private Random random = new Random(seed);

	// Default Graph constructor
	public Graph() {
		// Initialize all the instance variables
		nodes = new HashSet<>();
		edges = new HashSet<>();
		graphHashMap = new HashMap<>();
	}

	// Add node method used to a node into the Set of nodes
	// This method takes an integer value
	public boolean addNode(int id) {
		return nodes.add(new Node(id));
	}

	// overloaded addNode method which takes a node
	// and adds it to the Set of nodes
	public boolean addNode(Node n) {
		return nodes.add(n);
	}

	// overloaded addNode method which takes a collection of nodes
	// and add them all to the Set of nodes
	public boolean addNodes(Collection<Node> node) {
		return this.nodes.addAll(node);
	}

	// Remove Node method used to remove a node from the Set of nodes
	// Takes in an integer and creates a node before adding it to the Set
	public boolean removeNode(int id) {
		return nodes.remove(new Node(id));
	}

	// overloaded Remove Node method
	// Takes in a node and adds it to the Set
	public boolean removeNode(Node n) {
		return nodes.remove(n);
	}

	// Add Edge method used to add edge into the HashMap
	public boolean addEdge(Edge e) {
		// If adding the edge to the Set of edges returns false
		// Then it already exist no need to add it
		if (!edges.add(e)) {
			System.out.println("Not adding because this Edge exists already: " + e);
			return false;
		}

		System.out.println("Adding new edge: " + e);
		// check to see if the keys for the edges exist in the hashmap
		// if they don't put in the keys and set the pair value to an empty HashSet
		graphHashMap.putIfAbsent(e.getSource(), new HashSet<>());
		graphHashMap.putIfAbsent(e.getTarget(), new HashSet<>());

		// Add the edge to both keys
		graphHashMap.get(e.getSource()).add(e);
		graphHashMap.get(e.getTarget()).add(e);

		return true;
	}

	// Overridden addEdge method which takes to integer values
	// These values used to create two node objects and a edge object from the node
	// objects
	// Which is then passed to the addEdge method above.
	// This is implemented this way to allow develops to decide how they would like
	// to add a new Edge
	public boolean addEdge(int nodeID1, int nodeID2) {
		return addEdge(new Edge(new Node(nodeID1), new Node(nodeID2)));
	}

	// removeEdge method used to remove edges from the HashMap
	public boolean removeEdge(Edge e) {
		// If removing the edge from the Set of edges return false
		// Then it has been already removed
		if (!edges.remove(e)) {
			return false;
		}

		// Retrieve all the edges related to each node in the edge from the HashMap
		Set<Edge> edgesForSourceNode = graphHashMap.get(e.getSource());
		Set<Edge> edgesForTargetNode = graphHashMap.get(e.getTarget());

		// if the Set of edges returned for the source node
		// is not null remove the edge from the hashmap
		if (edgesForSourceNode != null) {
			edgesForSourceNode.remove(e);
			this.edges.remove(e);// remove edge from the Set of edges

		}
		// if the Set of edges returned for the target node
		// is not null remove the edge from the hashmap
		if (edgesForTargetNode != null) {
			edgesForTargetNode.remove(e);
			this.edges.remove(e);// remove edge from the Set of edges
		}

		return true;
	}

	// Overridden removeEdge method which takes two integer values
	// These values are used to create two node objects and a edge object from the
	// node
	// objects
	// Which is then passed to the removeEdge method above.
	// This is implemented this way to allow develops to decide how they would like
	// to remove an Edge
	public boolean removeEdge(int source, int target) {
		return removeEdge(new Edge(new Node(source), new Node(target)));
	}

	// Breath First Search method
	// Used to find paths through a graph
	public boolean bfs(Node start, Node goal) {
		System.out.println("BFS Starting");
		// If the start node is the goal node then stop
		if (start.equals(goal)) {
			System.out.println("Goal Node Found!");
			System.out.println(start);
		}
		// Create two linked lists to store the nodes queued and the nodes explored
		Queue<Node> queue = new LinkedList<>();
		Queue<Node> explored = new LinkedList<>();
		// Add the start node to the queue
		queue.add(start);
		// Print the id of the start node
		System.out.println("Root node: " + start.getID());

		// While the queue is not empty
		while (!queue.isEmpty()) {
			// Remove the node from the top of the queue
			// put it into a node object
			Node current = queue.remove();
			// if the node is in explored queue skip
			if (!explored.contains(current)) {
				// set the current node to visited
				current.setVisited(true);
				// if current is the goal node
				if (current.equals(goal)) {
					// print out that it has been found and stop the BFS
					System.out.println("Goal Node Found!");
					System.out.println("Path explored to reach the goal node: ");
					// Loop over explored array and show the path take to find the goal node
					for (Node n : explored) {
						System.out.print("Node: " + n.getID() + ", ");
					}
					System.out.println();
					return true;
				} else {
					// if the current node is not connect to any other node
					if (getAdjNodes(current).isEmpty()) {
						// print out the path was not found
						System.out.println("Path not found");
						return false;
					} else {
						// else put all the nodes connected to current into the queve
						queue.addAll(getAdjNodes(current));
					}
				}
				// add the current node to the explored queue
				explored.add(current);

			}
		}

		return false;

	}

	// Method used to build a graph
	// Takes in the number of nodes and the threshold for
	// the Bernoulli distribution of when an edge gets added or not
	public void buildGraph(int N, double threshold) {

		// Create an ArrayList of nodes
		ArrayList<Node> nodes = new ArrayList<>();

		// Loop until i < N the number of nodes
		for (int i = 0; i < N; i++) {
			// On each iteration the i value is passed
			// to the addNode method
			// which creates and adds the node to the Set of Nodes
			this.addNode(i);
		}
		// get all the nodes just created and put them into the ArrayList
		nodes.addAll(this.getNodes());
		// Loop over the arrayList of nodes
		for (int source = 0; source < nodes.size(); source++) {
			// Nested Loop one step ahead of the first loop
			// This ensures that All nodes get the chance to have an edge with N-1 nodes
			for (int target = source + 1; target < nodes.size(); target++) {
				// Bernoulli distribution like a coin flip its either a yes or a no
				// This boolean equation is used to determine
				// when an edge is added to the graph
				if (this.bernoulli(threshold)) {
					// if returns true then add an edge
					// using the integer values stored in the local variables
					// source and edge
					this.addEdge(source, target);
				}
			}
		}

	}

	// Returns a random real number uniformly in [0, 1).
	public double uniform() {
		return random.nextDouble();
	}

	// Returns a random boolean from a Bernoulli distribution with success
	// probability
	public boolean bernoulli(double p) {
		if (!(p >= 0.0 && p <= 1.0))
			throw new IllegalArgumentException("probability p must be between 0.0 and 1.0: " + p);
		return uniform() < p;
	}

	// Returns a random boolean from a Bernoulli distribution with success
	// probability 1/2.
	public boolean bernoulli() {
		return bernoulli(0.5);
	}

	// getAdjNodes(Node n) method gets all the nodes 
	// linked to the node object passed in
	public Set<Node> getAdjNodes(Node n) {
		// A Lambda expression is used to do this as it is the quickest way.
		// It is functional programming with object oriented program new to Java 8
		// The code below works as follows the node object being passed is used 
		// as the key value to retrieve the HashSet of Edges related to the key 
		// in the HashMap “graphHashMap”. The lambda expression then converts the 
		// HashSet of Edges into a stream which then allows the stream to be map
		// all the nodes connected via an edge to the node that was passed in.
		// The related nodes can be either a source or target in any one of the edges in
		// the HashSet of Edges that is in the stream. The lambda expression only 
		// retrieves any node from the HashSet of Edges that is not the node object that
		// passed in this is then collected and converted to a Set of nodes.
		return graphHashMap.get(n).stream().map(e -> e.getSource().equals(n) ? e.getTarget() : e.getSource())
				.collect(Collectors.toSet());
	}

	// get the average number of edges for a node in the graph
	public double getEdgeAvgPerNode() {
		return (double) this.getEdges().size() / (double) this.getGraphHashMap().keySet().size();

	}

	// Getter and Setter Methods for the instance variable
	// Adheres to encapsulation
	public Set<Node> getNodes() {
		return nodes;
	}

	public Set<Edge> getEdges() {
		return edges;
	}

	public Map<Node, Set<Edge>> getGraphHashMap() {
		return graphHashMap;
	}

	@Override
	public String toString() {
		return "Graph adjList=" + graphHashMap + "]";
	}

}
