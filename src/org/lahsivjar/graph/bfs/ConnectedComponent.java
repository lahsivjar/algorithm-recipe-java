package org.lahsivjar.graph.bfs;

import org.lahsivjar.graph.Graph;
import org.lahsivjar.graph.GraphLibrary;

/**
 * Graph search (both DFS and BFS) can be used to find connected components for undirected graph. For undirected graph
 * a connected component means that every vertex is reachable from every other vertex.
 *
 * Note: This method will also find weekly connected component in a directed graph since weekly connected component
 * ignore direction of edges. However, in this class we just consider connectivity of undirected graphs.
 */
public class ConnectedComponent extends BFS {

    public ConnectedComponent(Graph g) {
        super(g);
        if (g.isDirected()) {
            throw new IllegalArgumentException("Only for undirected graphs");
        }
    }

    public int findConnectedComponents() {
        int ccCount = 0;
        for (int u : graph.getVertices()) {
            if (!discovered[u]) {
                ccCount++;
                bfs(u);
            }
        }
        return ccCount;
    }

    @Override
    void processVertexEarly(int u) {

    }

    @Override
    void processEdge(int u, int v) {

    }

    @Override
    void processVertexLate(int v) {

    }

    public static void main(String[] args) {
        final Graph testGraph = GraphLibrary.graph8(false);

        final ConnectedComponent cc = new ConnectedComponent(testGraph);
        System.out.println("Number of connected components: " + cc.findConnectedComponents());
    }
}
