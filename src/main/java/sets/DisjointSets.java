package sets;

/** A class that represents the Disjoint Sets data structure. Please refer
 * to the lecture slides.
 * This class is used in Kruskal's. Do not change anything in this class.
 * */
public class DisjointSets {
    private int[] parent;

    public void createSets(int n) {
        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = -1;
        }
    }

    /**
     * Returns the root of the "tree" that x belongs to. Uses path compression
     * heuristic.
     * @param x node id
     * @return root of the tree that x belongs to
     */
    public int find(int x) {
        while (parent[x] >= 0)
            x = parent[x];
        return x;
    }

    /**
     * Merges the trees of x and y.
     * @param x node id
     * @param y node id
     */
    public void union(int x, int y) {
        // find the roots
        x = find(x);
        y = find(y);

        // both nodes are already part of one tree
        if (x == y)
            return;

        if (parent[x] < parent[y]) {
            parent[y] = x;
        } else {
            if (parent[x] == parent[y])
                parent[y]--;
            parent[x] = y;
        }
    }

}

