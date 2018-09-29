package org.lahsivjar.graph.bfs;

import org.lahsivjar.graph.Graph;
import org.lahsivjar.graph.GraphLibrary;

/**
 * Simple search using BFS
 */
public class SimpleSearch extends BFS {

    public SimpleSearch(Graph g) {
        super(g);
    }

    @Override
    void processVertexEarly(int u) {

    }

    @Override
    void processEdge(int u, int v) {

    }

    @Override
    void processVertexLate(int v) {
        System.out.println("Processed: " + v);
    }

    public static void main(String[] args) {
        final Graph testGraph = GraphLibrary.graph2(false);
        testGraph.printGraph();
        final SimpleSearch search = new SimpleSearch(testGraph);
        search.bfs(0);
    }
}
