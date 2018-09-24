package org.lahsivjar.graph.dfs;

import org.lahsivjar.graph.Graph;
import org.lahsivjar.graph.GraphLibrary;

/**
 * Prints the order of the vertices as per their processed order
 */
public class SimpleSearch extends DFS {

    public SimpleSearch(Graph g) {
        super(g);
    }

    @Override
    void processVertexEarly(final int u) {

    }

    @Override
    void processEdge(final int u, final int v) {

    }

    @Override
    void processVertexLate(final int u) {
        System.out.println("Processed: " + u);
    }

    public static void main(String[] args) {
        final Graph testGraph = GraphLibrary.graph2(false);
        testGraph.printGraph();

        final SimpleSearch search = new SimpleSearch(testGraph);
        search.dfs(0);

        search.printState();
    }
}
