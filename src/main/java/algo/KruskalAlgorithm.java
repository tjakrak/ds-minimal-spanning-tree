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
        DisjointSets nSet = new DisjointSets();
        ArrayList<Edge> sortedEdge = new ArrayList<>();
        nSet.createSets(numNodes());

        Edge temp;
        for (int i = 0; i < numNodes(); i++) {
            temp = getFirstEdge(i);
            while (temp != null) {
                sortedEdge.add(temp);
                temp = temp.next();
            }
        }

        Collections.sort(sortedEdge);


    }

}

