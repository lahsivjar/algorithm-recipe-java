package org.lahsivjar.graph.bfs;

import org.lahsivjar.graph.Graph;
import org.lahsivjar.graph.GraphLibrary;

/**
 * A BFS over a unweighted graph from a source vertex, say u, gives a shortest path from u to every other vertex.
 * Note: This is true for both directed an undirected graph but only if the source vertex of shortest path is the node
 * on which bfs was executed.
 */
public class UnweightedShortestPath extends BFS {

    public UnweightedShortestPath(Graph g) {
        super(g);
    }

    public void findShortestPath(final int source, final int destination) {
        bfs(source);
        printParent();
        // After executing bfs parent array can be used to find shortest path from source -> destination
        printPath(destination);
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
        final Graph testGraph = GraphLibrary.graph5(true);

        final UnweightedShortestPath usp = new UnweightedShortestPath(testGraph);
        usp.findShortestPath(0, 6);
    }
}
