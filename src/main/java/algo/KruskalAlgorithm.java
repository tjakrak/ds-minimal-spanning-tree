package algo;

import graph.*;
import sets.DisjointSets;

import java.util.ArrayList;
import java.util.Collections;

/** Subclass of MSTAlgorithm. Computes MST of the graph using Kruskal's algorithm. */
public class KruskalAlgorithm extends MSTAlgorithm {

    /**
     * Constructor for KruskalAlgorithm. Takes the graph
     * @param graph input graph
     */
    public KruskalAlgorithm(Graph graph) { super(graph); }

    /**
     * Compute minimum spanning tree for this graph. Add edges of MST to
     * edgesMST list. Should use Kruskal's algorithm and DisjointSets class.
     */
    @Override
    public void computeMST() {
        DisjointSets nSets = new DisjointSets();
        ArrayList<Edge> sortedEdge = new ArrayList<>();
        nSets.createSets(numNodes());

        // get all the unsorted edges from all the nodes and store it to an ArrayList
        Edge temp;
        for (int i = 0; i < numNodes(); i++) {
            temp = getFirstEdge(i);
            while (temp != null) {
                sortedEdge.add(temp);
                temp = temp.next();
            }
        }

        // sort all the unsorted edges according to the priority
        Collections.sort(sortedEdge);

        // Iterate until we go through all the sorted edges from the ArrayList
        for (Edge e : sortedEdge) {
            int root1 = nSets.find(e.getId1());
            int root2 = nSets.find(e.getId2());
            // if the root of the edge is not the same, then we will add the edge to the MSTEdge
            // and connect both roots of the edges (to whichever has the biggest height)
            if (root1 != root2) {
                addMSTEdge(e);
                nSets.union(e.getId1(), e.getId2());
            }
        }
    }

}

