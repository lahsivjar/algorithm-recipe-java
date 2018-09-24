package org.lahsivjar.graph.dfs;

import org.lahsivjar.graph.Graph;
import org.lahsivjar.graph.GraphLibrary;

public class CycleDetectionUndirectedGraph extends DFS {

    public CycleDetectionUndirectedGraph(Graph g) {
        super(g);
        if (g.isDirected()) {
            throw new IllegalArgumentException("Only for undirected graphs");
        }
    }


    @Override
    void processVertexEarly(int u) {

    }

    @Override
    void processEdge(int u, int v) {
        // u -> v
        // If this is a back edge to a discovered vertex then it is a cycle
        if (discovered[v]) {
            // Cycle is from v -(existing path in the DFS graph)-> u -> v
            System.out.println("Detected cycle:");
            printPath(v, u);
            System.out.println(v);
        }
    }

    @Override
    void processVertexLate(int u) {

    }

    public static void main(String[] args) {
        final Graph testGraph = GraphLibrary.graph1(false);

        final CycleDetectionUndirectedGraph cycle = new CycleDetectionUndirectedGraph(testGraph);
        cycle.dfs(0);
    }
}
