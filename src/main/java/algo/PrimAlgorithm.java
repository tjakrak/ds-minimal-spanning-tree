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
        // FILL IN CODE
        // Note: must use a MinHeap and be efficient
        int numNd = numNodes();
        MinHeap pQueue = new MinHeap(numNodes());
        table = new int[numNodes()][3];
        initiateTable();

        Edge temp;
        int ct = 0;
        int index = sourceVertex;
        while (ct < numNodes()) {
            temp = getFirstEdge(index);
            updateAdded(index);
            while (temp != null) {
                if (!hasBeenAdded(temp.getId2())) {
                    pQueue.reduceKey(temp.getId2(), temp.getCost());
                    updateCostAndPath(temp.getId2(), temp.getCost(), index);
                }
                temp = temp.next();
            }

            int minIndex = pQueue.removeMin();

            Edge newEdge = new Edge(getPath(minIndex), minIndex, getCost(minIndex));
            addMSTEdge(newEdge);
            index = minIndex;
            ct++;
        }

    }

    /**
     * | f |    c    |  p |
     * | 1 |    0    | -1 |
     * | 0 | int.max | -1 |
     * | 0 | int.max | -1 |
     */
    private void initiateTable() {
        for (int i = 0; i < table.length; i++) {
            table[i][0] = 0;
            table[i][1] = Integer.MAX_VALUE;
            table[i][2] = -1;
        }

        table[sourceVertex][0] = 1;
        table[sourceVertex][1] = 0;
        table[sourceVertex][2] = sourceVertex;
    }

    private void updateAdded(int vertex) {
        table[vertex][0] = 1;
    }

    private Boolean hasBeenAdded(int vertex) {
        if (table[vertex][0] == 0) {
            return false;
        } else {
            return true;
        }
    }

    private void updateCostAndPath(int vertex, int cost, int path) {
        if (table[vertex][0] == 0 && table[vertex][1] > cost) {
            table[vertex][1] = cost;
            table[vertex][2] = path;
        }
    }

    private int getCost(int vertex) {
        return table[vertex][1];
    }

    private int getPath(int vertex) {
        return table[vertex][2];
    }

}
