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

public class Graph {
	private Set<Node> nodes;
	private Set<Edge> edges;
	private Map<Node, Set<Edge>> graphHashMap;

	private long seed = System.currentTimeMillis();
	private Random random = new Random(seed);

	public Graph() {
		nodes = new HashSet<>();
		edges = new HashSet<>();
		graphHashMap = new HashMap<>();
	}

	public boolean addNode(int id) {
		return nodes.add(new Node(id));
	}

	public boolean addNode(Node n) {
		return nodes.add(n);
	}

	public boolean addNodes(Collection<Node> node) {
		return this.nodes.addAll(node);
	}

	public boolean removeNode(int id) {
		return nodes.remove(new Node(id));
	}

	public boolean removeNode(Node n) {
		return nodes.remove(n);
	}

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

	public boolean addEdge(Edge e) {
		if (!edges.add(e)) {
			System.out.println("Not adding " + e);
			return false;
		}
		System.out.println("Adding " + e);
		graphHashMap.putIfAbsent(e.getSource(), new HashSet<>());
		graphHashMap.putIfAbsent(e.getTarget(), new HashSet<>());

		graphHashMap.get(e.getSource()).add(e);
		graphHashMap.get(e.getTarget()).add(e);

		return true;
	}

	public boolean addEdge(int nodeID1, int nodeID2) {

		return addEdge(new Edge(new Node(nodeID1), new Node(nodeID2)));
	}

	public void buildGraph(int N, double treshold) {

		ArrayList<Node> nodes = new ArrayList<>();

		for (int i = 0; i < N; i++) {
			this.addNode(i);
		}

		// double p = (double) E / (N * (N - 1) / 2.0);

		nodes.addAll(this.getNodes());
		for (int source = 0; source < nodes.size(); source++) {
			for (int target = source + 1; target < nodes.size(); target++) {
				if (this.bernoulli(treshold)) {
					this.addEdge(source, target);
				}
			}
		}

	}

	/**
	 * Returns a random real number uniformly in [0, 1).
	 *
	 * @return a random real number uniformly in [0, 1)
	 */
	public double uniform() {
		return random.nextDouble();
	}

	/**
	 * Returns a random boolean from a Bernoulli distribution with success
	 * probability <em>p</em>.
	 *
	 * @param p
	 *            the probability of returning {@code true}
	 * @return {@code true} with probability {@code p} and {@code false} with
	 *         probability {@code 1 - p}
	 * @throws IllegalArgumentException
	 *             unless {@code 0} &le; {@code p} &le; {@code 1.0}
	 */
	public boolean bernoulli(double p) {
		if (!(p >= 0.0 && p <= 1.0))
			throw new IllegalArgumentException("probability p must be between 0.0 and 1.0: " + p);
		return uniform() < p;
	}

	/**
	 * Returns a random boolean from a Bernoulli distribution with success
	 * probability 1/2.
	 * 
	 * @return {@code true} with probability 1/2 and {@code false} with probability
	 *         1/2
	 */
	public boolean bernoulli() {
		return bernoulli(0.2);
	}

	public boolean removeEdge(Edge e) {
		if (!edges.remove(e)) {

			return false;
		}
		Set<Edge> edgesForSourceNode = graphHashMap.get(e.getSource());
		Set<Edge> edgesForTargetNode = graphHashMap.get(e.getTarget());

		if (edgesForSourceNode != null) {
			edgesForSourceNode.remove(e);
			this.edges.remove(e);

		}
		if (edgesForTargetNode != null) {
			edgesForTargetNode.remove(e);
			this.edges.remove(e);
		}

		return true;
	}

	public boolean removeEdge(int source, int target) {
		return removeEdge(new Edge(new Node(source), new Node(target)));
	}

	public Set<Node> getAdjNodes(Node n) {
		return graphHashMap.get(n).stream().map(e -> e.getSource().equals(n) ? e.getTarget() : e.getSource())
				.collect(Collectors.toSet());
	}

	public double getEdgeAvgPerNode() {
		return (double) this.getEdges().size() / (double) this.getGraphHashMap().keySet().size();

	}

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
