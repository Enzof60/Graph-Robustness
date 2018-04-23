package graph;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;

import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

public class Tester extends JFrame implements JSONAware, Serializable {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);
		System.out.println("Enter the number of Nodes");
		int V = input.nextInt();
		input.nextLine();
		/*
		 * System.out.println("Enter the number of Edges"); int E = input.nextInt();
		 */
		System.out.println("Enter the Threshold of Edges");
		double T = input.nextDouble();
		Graph g = new Graph();
		g.buildGraph(V, T);
		JSONObject job = new JSONObject();
		job.put("nodes", g.getNodes());
		job.put("edges", g.getEdges());
		int count = 1;
		

		int val = 0, choice = 0;

		do {
			
			writeToFile(job.toString(), "graph"+count+".json");

			System.out.println("Number of nodes:" + g.getNodes().size());
			System.out.println("Number of Edges:" + g.getEdges().size());
			System.out.println("Number of nodes with edges: " + g.getGraphHashMap().keySet().size());
			System.out.println("Average  number of edges per node: " + g.getEdgeAvgPerNode());
			System.out.print("\n\n1. BFS:" + "\n2. Remove edge:" + "\n3. Rebuild Graph" + "\n4. Add new node"
					+ "\n5. Add new edge" + "\n6. Random removal and addition of edges" + "\nChoice:");
			choice = input.nextInt();
			if (choice == 1) {
				System.out.println("Enter Start node: ");
				int start = input.nextInt();
				System.out.println("Enter goal node: ");
				int goal = input.nextInt();

				g.bfs(new Node(start), new Node(goal));

			} else if (choice == 2) {
				count++;
				// Create an arraylist to hold the nodes
				ArrayList<Node> nodes = new ArrayList<>();
				// put all the nodes in the graph into the arraylist
				nodes.addAll(g.getNodes());
				// prompt user to enter in the node
				System.out.println("Enter the node you would like to remove the edges from: ");
				// put the user input into a variable
				val = input.nextInt();
				// try catch to catch errors
				try {
					// get the node from the arrayList and put it into a node object
					Node v = nodes.get(val);
					// Create an arrayList of edges
					ArrayList<Edge> edge = new ArrayList<>();
					// get all the edges related to the node
					edge.addAll(g.getGraphHashMap().get(v));
					// print to the user how many nodes are connected to the Edges
					System.out.println("Edges connected to node: " + v.getID() + " is " + edge.size());

					// create an arraylist of nodes that need to be linked after edges been removed
					ArrayList<Node> nodesToLink = new ArrayList<>();
					// Loop over the edge array
					for (Edge e : edge) {
						// if the edges source node value is equal to the user input node
						if (e.getSource().getID() == v.getID()) {
							// then put the target node of the edge in the nodesToLink array
							nodesToLink.add(e.getTarget());
						} else {
							// put the source node of the edge in the nodesToLink array
							nodesToLink.add(e.getSource());
						}
						// remove the edge
						g.removeEdge(e);
						
					}
					// print to the user the number of nodes that need to be joined
					System.out.println("Number of nodes that need to be joined: " + (nodesToLink.size()));
					// if the array has more the one value
					if (nodesToLink.size() - 1 > 0) {
						// then loop over the array
						for (int i = 0; i < nodesToLink.size(); i++) {
							// Check to see that the item in the current iteration is not equal to the next
							// item in the array
							// and there is no edge already between the two
							if (nodesToLink.get(i).getID() != nodesToLink.get(i + 1).getID()
									&& (g.addEdge(nodesToLink.get(i).getID(), nodesToLink.get(i + 1).getID()) != false||
									g.addEdge(nodesToLink.get(i+1).getID(), nodesToLink.get(i).getID()))) {
								// print to the user what nodes are being added together
								System.out.println("Adding node: " + nodesToLink.get(i).getID() + " to node: "
										+ nodesToLink.get(i + 1).getID());
								// add the edge
								g.addEdge(nodesToLink.get(i).getID(), nodesToLink.get(i + 1).getID());
							}

						}
					} else {
						// else the there is only one item in the array
						Random ran = new Random();
						// get a random number between the size of the nodes with edges
						int rand = ran.nextInt(g.getGraphHashMap().keySet().size());
						// create an array of nodes that have edges
						ArrayList<Node> nodesWEdges = new ArrayList<>();
						// add all the nodes to the array
						nodesWEdges.addAll(g.getGraphHashMap().keySet());
						// try catch to catch errors
						try {

							System.out.println("Only one node");
							System.out.println("Adding node " + nodesToLink.get(nodesToLink.size() - 1).getID()
									+ " to node " + nodesWEdges.get(rand).getID());
							// Add an edge between the single node to a random node in the graph that has an
							// edge
							g.addEdge(nodesToLink.get(nodesToLink.size() - 1).getID(), nodesWEdges.get(rand).getID());
						} catch (Exception e) {
							System.out.println("Error: " + e.getMessage());
						}

					}

					// print the number of edges after the typography has changed
					System.out.println("Number of nodes:" + g.getNodes().size());
					System.out.println("Number of Edges:" + g.getEdges().size());
					System.out.println("Average  number of edges per node: " + g.getEdgeAvgPerNode());

					// make a json object of the new graph
					job = new JSONObject();
					job.put("nodes", g.getNodes());
					job.put("edges", g.getEdges());
					// write to a file to be displayed on the browser
					writeToFile(job.toString(), "graph" + count + ".json");

				} catch (Exception e) {
					// TODO: handle exception
					// if user enters an invalid node print out the error messages
					if (val >= 0 && val >= (g.getNodes().size())) {

						System.out.println("The node entered must be in the range of 0 to " + (g.getNodes().size()));

					} else if (val < 0) {
						System.out.println("Program terminated");
					}
				}
			} else if (choice == 3) {
				count++;
				System.out.println("Enter the number of Nodes");
				V = input.nextInt();
				input.nextLine();
				/*
				 * System.out.println("Enter the number of Edges"); E = input.nextInt();
				 */
				System.out.println("Enter the Threshold value must be between 0 and 1");
				T = input.nextDouble();
				g = new Graph();
				g.buildGraph(V, T);
				System.out.println("Number of nodes:" + g.getNodes().size());
				System.out.println("Number of Edges:" + g.getEdges().size());
				System.out.println("Number of nodes with edges: " + g.getGraphHashMap().keySet().size());
				System.out.println("Average  number of edges per node: " + g.getEdgeAvgPerNode());
				job = new JSONObject();
				job.put("nodes", g.getNodes());
				job.put("edges", g.getEdges());
				writeToFile(job.toString(), "graph" + count + ".json");
			} else if (choice == 4) {
				count++;
				System.out.println("Create a new node");
				System.out.println("Enter the id for the node");
				V = input.nextInt();
				input.nextLine();

				g.addNode(V);
				job = new JSONObject();
				job.put("nodes", g.getNodes());
				job.put("edges", g.getEdges());
				writeToFile(job.toString(), "graph" + count + ".json");
				System.out.println("Number of nodes:" + g.getNodes().size());
				System.out.println("Number of Edges:" + g.getEdges().size());
				System.out.println("Number of nodes with edges: " + g.getGraphHashMap().keySet().size());
				System.out.println("Avarage number of edges per node: " + g.getEdgeAvgPerNode());
			} else if (choice == 5) {
				count++;
				System.out.println("Create a new Edge");
				System.out.println("Enter the source node for the edge");
				V = input.nextInt();
				input.nextLine();
				System.out.println("Enter the target node for the edge");
				int E = input.nextInt();
				input.nextLine();

				g.addEdge(V, E);
				job = new JSONObject();
				job.put("nodes", g.getNodes());
				job.put("edges", g.getEdges());
				writeToFile(job.toString(), "graph" + count + ".json");
				System.out.println("Number of nodes:" + g.getNodes().size());
				System.out.println("Number of Edges:" + g.getEdges().size());
				System.out.println("Number of nodes with edges: " + g.getGraphHashMap().keySet().size());
				System.out.println("Avarage number of edges per node: " + g.getEdgeAvgPerNode());
			} else if (choice == 6) {
				int E;
				Random random = new Random();
				for (int i = 0; i < (g.getNodes().size() / 2); i++) {
					count++;
					if (!g.bernoulli(random.nextFloat())) {
						System.out.println("Adding a random edge ");
						V = random.nextInt(g.getNodes().size() - 1);
						E = random.nextInt(g.getNodes().size() - 1);
						if (V != E) {
							System.out.println("V :" + V + " E:" + E);
							g.addEdge(V, E);
							job = new JSONObject();
							job.put("nodes", g.getNodes());
							job.put("edges", g.getEdges());
							writeToFile(job.toString(), "graph" + count + ".json");
							System.out.println("Number of nodes:" + g.getNodes().size());
							System.out.println("Number of Edges:" + g.getEdges().size());
							System.out.println("Number of nodes with edges: " + g.getGraphHashMap().keySet().size());
							System.out.println("Average  number of edges per node: " + g.getEdgeAvgPerNode());
						} else {
							System.err.println("V :" + V + " E:" + E + " No Loops");
						}

					} else {
						// Create an arraylist to hold the nodes
						ArrayList<Node> nodes = new ArrayList<>();
						// put all the nodes in the graph into the arraylist
						nodes.addAll(g.getNodes());
						// prompt user to enter in the node

						// put the user input into a variable
						val = random.nextInt(g.getNodes().size() - 1);

						System.out.println("Removing random nodes edges");
						System.out.println("Random node position in array selected: " + val);
						// try catch to catch errors

						// get the node from the arrayList and put it into a node object

						Node v = nodes.get(val);
						// Create an arrayList of edges
						ArrayList<Edge> edge = new ArrayList<>();
						// get all the edges related to the node
						if (g.getGraphHashMap().get(v) != null) {
							edge.addAll(g.getGraphHashMap().get(v));
							// print to the user how many nodes are connected to the Edges
							System.out.println("The node in position " + val + " is node: " + v.getID()
									+ " \nThe number of edges it has is: " + edge.size());

							// create an arraylist of nodes that need to be linked after edges been removed
							ArrayList<Node> nodesToLink = new ArrayList<>();
							// Loop over the edge array
							for (Edge e : edge) {
								// if the edges source node value is equal to the user input node
								if (e.getSource().getID() == v.getID()) {
									// then put the target node of the edge in the nodesToLink array
									nodesToLink.add(e.getTarget());
								} else {
									// put the source node of the edge in the nodesToLink array
									nodesToLink.add(e.getSource());
								}
								// remove the edge
								g.removeEdge(e);
							}

							// print to the user the number of nodes that need to be joined
							System.out.println("Number of nodes that need to be joined: " + (nodesToLink.size()));
							// if the array has more the one value
							if (nodesToLink.size() - 1 > 0) {
								// then loop over the array
								for (int j = 0; j < nodesToLink.size() - 1; j++) {
									// Check to see that the item in the current iteration is not equal to the next
									// item in the array
									// and there is no edge already between the two
									if (nodesToLink.get(j).getID() != nodesToLink.get(j + 1).getID()
											&& g.addEdge(nodesToLink.get(j).getID(),
													nodesToLink.get(j + 1).getID()) != false) {
										// print to the user what nodes are being added together
										System.out.println("Adding node: " + nodesToLink.get(j).getID() + " to node: "
												+ nodesToLink.get(j + 1).getID());
										// add the edge
										g.addEdge(nodesToLink.get(j).getID(), nodesToLink.get(j + 1).getID());
									} else {
										System.out
												.println("This Edge was not created because it already exits an Edge\n"
														+ "between " + nodesToLink.get(j).getID() + " and "
														+ nodesToLink.get(j + 1).getID());
									}
								}

							} else {
								// else the there is only one item in the array
								Random ran = new Random();
								// get a random number between the size of the nodes with edges
								int rand = ran.nextInt(g.getGraphHashMap().keySet().size());
								// create an array of nodes that have edges
								ArrayList<Node> nodesWEdges = new ArrayList<>();
								// add all the nodes to the array
								nodesWEdges.addAll(g.getGraphHashMap().keySet());
								// try catch to catch errors
								try {

									System.out.println("Only one node");
									if(v.getID() != nodesWEdges.get(rand).getID()) {
									System.out.println(
											"Adding node " + v.getID() + " to node " + nodesWEdges.get(rand).getID());
									// Add an edge between the single node to a random node in the graph that has an
									// edge
									g.addEdge(v.getID(), nodesWEdges.get(rand).getID());
									}else {
										System.out.println("NO loops: "+v.getID() +" == "+ nodesWEdges.get(rand).getID());
									}
								} catch (Exception e) {
									System.out.println("Error2: " + e.getMessage());
								}

							}

							// print the number of edges after the typography has changed
							System.out.println("Number of nodes:" + g.getNodes().size());
							System.out.println("Number of Edges:" + g.getEdges().size());
							System.out.println("Number of nodes with edges: " + g.getGraphHashMap().keySet().size());
							System.out.println("Average  number of edges per node: " + g.getEdgeAvgPerNode());
							// make a json object of the new graph
							job = new JSONObject();
							job.put("nodes", g.getNodes());
							job.put("edges", g.getEdges());
							// write to a file to be displayed on the browser
							writeToFile(job.toString(), "graph" + count + ".json");

						}

					}

				}
			}
		} while (val != -1);

		input.close();
		System.exit(0);
	}

	// write to file method
	public static void writeToFile(String s, String filename) {
		// try catch to stop it from crashing if it can't write to file
		FileWriter fileWriter = null;
		BufferedWriter writer = null;
		try {
			File file = new File("C://wamp64/www/" + filename);
			// create a file writer makes it possible to write characters to a file
			if (!file.exists()) {
				file.createNewFile();
			}

			// true = append file
			fileWriter = new FileWriter(file.getAbsoluteFile(), false);

			// create BufferedWriter to allow us to buffer the characters that are being
			// writen to the file
			writer = new BufferedWriter(fileWriter);
			// for each element in the arr
			writer.write(s);
			// close the BufferedWriter
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toJSONString() {
		// TODO Auto-generated method stub
		return null;
	}

}
