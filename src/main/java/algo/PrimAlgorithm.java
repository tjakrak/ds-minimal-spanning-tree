package algo;

import graph.*;
import priorityQueue.MinHeap;
import sets.DisjointSets;

/** Subclass of MSTAlgorithm. Uses Prim's algorithm to compute MST of the graph. */
public class PrimAlgorithm extends MSTAlgorithm {
    private int sourceVertex;
    private int[][] table;

    /**
     * Constructor for PrimAlgorithm. Takes the graph
     * @param graph input graph
     * @param sourceVertex the first vertex of MST
     */
    public PrimAlgorithm(Graph graph, int sourceVertex) {
        super(graph);
        this.sourceVertex = sourceVertex;
    }

    /**
     * Compute minimum spanning tree for this graph using Prim's algorithm.
     * Add edges of MST to edgesMST list.
     * */
    @Override
    public void computeMST() {
        // Note: must use a MinHeap and be efficient
        MinHeap pQueue = new MinHeap(numNodes());
        table = new int[numNodes()][3];
        initiateTable();

        Edge temp;
        int ct = 0;
        int index = sourceVertex;

        // update the cost of the source vertex in minheap
        pQueue.reduceKey(index, -1);

        // iterate until we visited all the nodes (cities)
        while (ct < numNodes()) {
            // get the vertexId with the smallest cost from minheap
            int minIndex = pQueue.removeMin();
            // update the Added column on the table to true
            updateAdded(index);

            // if it is not the sourceVertex
            if (getPath(minIndex) != -1) {
                Edge newEdge = new Edge(minIndex, getPath(minIndex), getCost(minIndex));
                addMSTEdge(newEdge);
            }

            // update the index to smallest VertexID
            index = minIndex;

            // visit all the edges on the smallest VertexID
            temp = getFirstEdge(index);
            while (temp != null) {
                // if the vertex hasn't been added yet
                if (!hasBeenAdded(temp.getId2())) {
                    // check if the new cost is smaller than the existing cost on the table
                    if (temp.getCost() < getCost(temp.getId2())) {
                        // update the cost in minheap
                        pQueue.reduceKey(temp.getId2(), temp.getCost());
                        // update the cost and path in the table
                        updateCostAndPath(temp.getId2(), temp.getCost(), index);
                    }
                }
                // go to the next edge
                temp = temp.next();
            }

            ct++;
        }

    }

    /**
     * Creating prim's table
     * | Added |    c    |  p |
     * | 0     |    0    | -1 |
     * | 0     | int.max | -1 |
     * | 0     | int.max | -1 |
     *
     * the sourceVertex (the vertex we want to start with) will have 0 cost
     * while the other vertexes will have int.max as the cost
     */
    private void initiateTable() {
        // initially each vertex will have value 0 (false), int.max, -1 except for the source vertex
        for (int i = 0; i < table.length; i++) {
            table[i][0] = 0;
            table[i][1] = Integer.MAX_VALUE;
            table[i][2] = -1;
        }

        // source vertex cost is 0
        table[sourceVertex][1] = 0;
    }

    /**
     * helper method to update Added to true
     *
     * @param vertex vertexID that we want to update
     */
    private void updateAdded(int vertex) {
        // update to 1 (true) if we find the shortest path
        table[vertex][0] = 1;
    }

    /**
     * helper method to check the boolean value of a vertex from Added column
     *
     * @param vertex vertexID
     */
    private Boolean hasBeenAdded(int vertex) {
        if (table[vertex][0] == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * helper method to update the cost and path of a particular vertex
     *
     * @param vertex vertexID
     */
    private void updateCostAndPath(int vertex, int cost, int path) {
        if (table[vertex][0] == 0 && table[vertex][1] > cost) {
            table[vertex][1] = cost;
            table[vertex][2] = path;
        }
    }

    /**
     * helper method to get the cost value
     *
     * @param vertex vertexID
     */
    private int getCost(int vertex) {
        return table[vertex][1];
    }

    /**
     * helper method to get the path Value
     *
     * @param vertex vertexID
     */
    private int getPath(int vertex) {
        return table[vertex][2];
    }

}
