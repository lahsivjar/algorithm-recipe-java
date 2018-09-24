package org.lahsivjar.graph.dfs;

import org.lahsivjar.graph.Graph;
import org.lahsivjar.graph.GraphLibrary;

import java.util.Arrays;

/**
 * Point 1: Conectivity of a graph is defined as the minimum number of vertices to remove so that the graph can be disconnected.
 * Point 2: An articulation vertex is a vertex which is a single point of failure i.e. removing it disconnects the graph.
 *
 * With both point 1 and point 2 under consideration a graph with connectivity 1 will have an articulation vertex or a
 * graph with an articulation vertex will have connectivity 1.
 */
public class ArticulationVertices extends DFS {

    // The earliest ancestor of the vertex to which it has a back edge in the DFS tree of the target graph
    private final int[] earliestReachableAncestor;

    // The out degree of a vertex in the DFS tree of the target graph
    private final int[] treeOutDegree;

    public ArticulationVertices(Graph g) {
        super(g);
        this.earliestReachableAncestor = new int[g.size()];
        this.treeOutDegree = new int[g.size()];
    }

    @Override
    void processVertexEarly(final int u) {
        // Initialize as itself
        earliestReachableAncestor[u] = u;
    }

    @Override
    void processEdge(final int u, final int v) {
        // When an edge u -> v is found then there are two cases:
        // 1. It might be a tree edge
        // 2. It might be a back edge

        if (isTreeEdge(u, v)) {
            treeOutDegree[u]++;
        }

        if (isBackEdge(u, v) && entryTime[v] < entryTime[earliestReachableAncestor[u]]) {
            // Note that this is not the final value. And may be updated depending on the earliest reachable
            // ancestor of the children of u i.e. if child of u has an earliest reachable ancestor which is
            // older than the current one of u then the value must update
            earliestReachableAncestor[u] = v;
        }
    }

    @Override
    void processVertexLate(final int u) {
        // First: we check for vertex u itself since DFS is LIFO
        final boolean isUroot = parent[u] == -1;

        // Check for root articulation vertex
        if (isUroot) {
            if (treeOutDegree[u] > 1) {
                System.out.println("Root articulation vertex: " + u);
            }
            return;
        }

        final boolean isParentOfUroot = parent[parent[u]] < 0;

        // If parent of u is root then there are two cases:
        // 1. It has more than 1 child (then root is a root articulation point (checked before)
        // 2. It has only 1 child then it cannot be a articulation point at all since it won't break the tree
        if (!isParentOfUroot) {
            if (earliestReachableAncestor[u] == u) {
                System.out.println("Bridge articulation vertex: " + parent[u]);
                if (treeOutDegree[u] > 0) {
                    // i.e. u is not a leaf node
                    System.out.println("Bridge articulation vertex: " + u);
                }
            }

            // NOTE: the difference between parent articulation vertex and bridge articulation vertex.
            // For a bridge articulation vertex earliest reachable ancestor of u must be u
            // For a parent articulation vertex earliest reachable ancestor of u must be parent of u
            if (earliestReachableAncestor[u] == parent[u]) {
                System.out.println("Parent articulation vertex: " + parent[u]);
            }
        }

        // Second: we update the earliest reachable ancestor for parent of u
        if (entryTime[earliestReachableAncestor[u]] < entryTime[earliestReachableAncestor[parent[u]]]) {
            earliestReachableAncestor[parent[u]] = earliestReachableAncestor[u];
        }
    }

    @Override
    public void printState() {
        super.printState();
        System.out.println("Earliest reachable ancestor:");
        System.out.println(Arrays.toString(earliestReachableAncestor));
        System.out.println("----------------------------------");
        System.out.println("Tree out degree:");
        System.out.println(Arrays.toString(treeOutDegree));
        System.out.println("----------------------------------");

    }

    private boolean isTreeEdge(final int u, final int v) {
        // u -> v
        return parent[v] == u;
    }

    private boolean isBackEdge(final int u, final int v) {
        // u -> v
        return
                // Should not be processed else cannot be a back edge
                !processed[v]
                // Should be discovered
                && discovered[v]
                // Should not be a tree edge
                && !isTreeEdge(u, v);
    }

    public static void main(String[] args) {
        final Graph testGraph = GraphLibrary.graph3(false);
        testGraph.printGraph();

        final ArticulationVertices av = new ArticulationVertices(testGraph);
        av.dfs(0);
        av.printState();
    }
}
