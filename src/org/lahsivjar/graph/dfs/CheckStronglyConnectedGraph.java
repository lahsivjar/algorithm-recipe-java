package org.lahsivjar.graph.dfs;

import org.lahsivjar.graph.Graph;
import org.lahsivjar.graph.GraphLibrary;

/**
 * A graph is known as strongly connected if every vertices can be reached by every other vertices. A directed graph
 * can be checked if strongly connected by:
 *
 * 1. doing a graph search from a vertex and checking if all nodes are visited
 *
 * but this is not enough, since it only checks that every vertex is reachable from the source vertex but we also
 * need to check if from every vertex source vertex is reachable so we do:
 *
 * 2. reverse all edges of the graph and again do a graph search from the source vertex over the modified graph.
 *
 * Doing 2 ensures that all vertices from source vertex since if source vertex can reach a vertex, say `v` on the
 * reversed graph means `v` can reach the source vertex on the original graph.
 *
 * Note that for an undirected graph there is no meaning of StronglyConnected. Instead undirected graphs use the
 * term connectivity which means every vertex is connected to every other by some path.
 */
public class CheckStronglyConnectedGraph extends DFS {

    public CheckStronglyConnectedGraph(Graph g) {
        super(g);
        if (!graph.isDirected()) {
            throw new IllegalArgumentException("Only for directed graphs");
        }
    }

    public boolean isStronglyConnected() {
        // Choose any arbitrary v and do a dfs
        final int arbitV = graph.getVertices().get(0);
        this.dfs(arbitV);

        // If any vertex is not reachable from `arbitV` then the graph cannot be strongly connected
        for (boolean d : discovered) {
            if (!d) {
                return false;
            }
        }

        // Now we run dfs on the edge complement graph with `arbitV` vertex as the source vertex
        final DFS dfsOverEdgeComplement = new DFS(graph.transpose()) {
            @Override
            void processVertexEarly(int u) {}

            @Override
            void processEdge(int u, int v) {}

            @Override
            void processVertexLate(int u) {}
        };

        dfsOverEdgeComplement.dfs(arbitV);

        for (boolean d : dfsOverEdgeComplement.discovered) {
            if (!d) {
                return false;
            }
        }

        // If arbitV is reachable from every other vertex and every other vertex is reachable from arbitV then
        // graph is strongly connected
        return true;
    }

    @Override
    void processVertexEarly(int u) {

    }

    @Override
    void processEdge(int u, int v) {

    }

    @Override
    void processVertexLate(int u) {

    }

    public static void main(String[] args) {
        final Graph testGraph = GraphLibrary.graph7(true);

        final CheckStronglyConnectedGraph scg = new CheckStronglyConnectedGraph(testGraph);
        if (scg.isStronglyConnected()) {
            System.out.println("Graph is strongly connected");
        } else {
            System.out.println("Graph is not strongly connected");
        }
    }
}
