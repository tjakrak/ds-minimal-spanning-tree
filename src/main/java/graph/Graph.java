package graph;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A class that represents a graph: stores the array of city nodes, the
 * adjacency list, as well as the hash table that maps city names to node ids.
 * Nodes are cities (of type CityNode); edges connect them and the cost of each edge
 * is the distance between the cities.
 * Fill in code in this class. You may add additional methods and variables.
 * You are required to implement a MinHeap from scratch, instead of using Java's built in PriorityQueue.
 */
public class Graph {
    private CityNode[] nodes; // nodes of the graph
    private Edge[] adjacencyList; // adjacency list; for each vertex stores a linked list of edges
    private int numEdges; // total number of edges
    // Add other variable(s) as needed:
    // add a HashMap to map cities to vertexIds.
    private HashMap<String, Integer>  cityIdAndName = new HashMap<>();

    /**
     * Constructor. Read graph info from the given file,
     * and create nodes and edges of the graph.
     *
     *   @param filename name of the file that has nodes and edges
     */
    public Graph(String filename) {

        try {
            FileReader f = new FileReader(filename);
            BufferedReader reader = new BufferedReader(f);
            String line;
            Boolean cityNodes = false;
            int totalCities = 0;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                String[] word = line.split("\\s+");
                if (word[0].equals("NODES")) {
                    cityNodes = true;
                    line = reader.readLine();
                    totalCities = Integer.parseInt(line);
                    nodes = new CityNode[totalCities];
                    adjacencyList = new Edge[totalCities];
                } else if (word[0].equals("ARCS")) {
                    cityNodes = false;
                } else if (cityNodes) {
                    String cityName = word[0];
                    double xCoordinate = Double.parseDouble(word[1]);
                    double yCoordinate = Double.parseDouble(word[2]);
                    CityNode newCityNode = new CityNode(cityName, xCoordinate, yCoordinate);
                    nodes[i] = newCityNode;
                    cityIdAndName.put(cityName, i);
                    i++;
                } else {
                    int id1 = cityIdAndName.get(word[0]);
                    int id2 = cityIdAndName.get(word[1]);
                    int cost = Integer.parseInt(word[2]);
                    Edge newEdge1 = new Edge(id1, id2, cost);
                    Edge newEdge2 = new Edge(id2, id1, cost);
                    addEdge(newEdge1, id1);
                    addEdge(newEdge2, id2);
                    numEdges += 2;
                }
            }
        } catch (IOException e) {
            System.out.println("No such file: " + filename);
        }
    }

    private void addEdge(Edge e, int sourceId) {
        if (adjacencyList[sourceId] == null) {
            adjacencyList[sourceId] = e;
        } else {
            Edge temp = adjacencyList[sourceId];
            if (temp.getId2() == e.getId2()) {
                System.out.println("Destination already exist");
                return;
            } else {
                while (temp.next() != null) {
                    temp = temp.next();
                    if (temp.getId2() == e.getId2()) {
                        System.out.println("Destination already exist");
                        return;
                    }
                }
                temp.setNext(e);
            }
        }
    }

    /**
     * Return the number of nodes in the graph
     * @return number of nodes
     */
    public int numNodes() {
        return nodes.length;
    }

    /** Return the head of the linked list that contains all edges outgoing
     * from nodeId
     * @param nodeId id of the node
     * @return head of the linked list of Edges
     */
    public Edge getFirstEdge(int nodeId) {
        return adjacencyList[nodeId];
    }

    /**
     * Return the edges of the graph as a 2D array of points.
     * Called from GUIApp to display the edges of the graph.
     *
     * @return a 2D array of Points.
     * For each edge, we store an array of two Points, v1 and v2.
     * v1 is the source vertex for this edge, v2 is the destination vertex.
     * This info can be obtained from the adjacency list
     */
    public Point[][] getEdges() {
        if (adjacencyList == null || adjacencyList.length == 0) {
            System.out.println("Adjacency list is empty. Load the graph first.");
            return null;
        }
        Point[][] edges2D = new Point[numEdges][2];
        int idx = 0;
        for (int i = 0; i < adjacencyList.length; i++) {
            for (Edge tmp = adjacencyList[i]; tmp != null; tmp = tmp.next(), idx++) {
                edges2D[idx][0] = nodes[tmp.getId1()].getLocation();
                edges2D[idx][1] = nodes[tmp.getId2()].getLocation();
            }
        }

        return edges2D;
    }

    /**
     * Get the nodes of the graph as a 1D array of Points.
     * Used in GUIApp to display the nodes of the graph.
     * @return a list of Points that correspond to nodes of the graph.
     */
    public Point[] getNodes() {
        if (nodes == null) {
            System.out.println("Array of nodes is empty. Load the graph first.");
            return null;
        }
        Point[] nodes = new Point[this.nodes.length];
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = this.nodes[i].getLocation();
        }

        return nodes;
    }

    /**
     * Used in GUIApp to display the names of the cities.
     * @return the list that contains the names of cities (that correspond
     * to the nodes of the graph)
     */
    public String[] getCities() {
        if (nodes == null) {
            return null;
        }
        String[] labels = new String[nodes.length];
        for (int i = 0; i < nodes.length; i++) {
            labels[i] = nodes[i].getCity();
        }

        return labels;

    }

    /**
     * Return the CityNode for the given nodeId
     * @param nodeId id of the node
     * @return CityNode
     */
    public CityNode getNode(int nodeId) {
        return nodes[nodeId];
    }


}
