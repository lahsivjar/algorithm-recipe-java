package org.lahsivjar.graph.bfs;

import org.lahsivjar.graph.Graph;
import org.lahsivjar.graph.GraphLibrary;

import java.util.Arrays;

/**
 * A bipartite graph(or a bi-graph) is a graph in which vertices can be categorized into two distinct sets such that
 * no two adjacent vertices in a graph belong to the same set.
 *
 * It can thought of as a graph coloring problem where we have to assign/color every vertex such that no two adjacent
 * vertices are of same color while minimizing the number of color used. If we can do this by using only two color we
 * have a bipartite or a bi graph.
 */
public class BipartiteGraph extends BFS {

    enum COLOR {
        BLACK,
        WHITE,
        UNCOLORED
    }

    private final COLOR[] vertexColor;
    // Track the bipartite status of this graph
    private boolean bipartite;

    public BipartiteGraph(Graph g) {
        super(g);
        vertexColor = new COLOR[g.size()];
    }

    public boolean isBipartite() {
        Arrays.fill(vertexColor, COLOR.UNCOLORED);
        bipartite = true;

        for (final int v : graph.getVertices()) {
            if (!discovered[v]) {
                // Start with a particular color for the source vertex
                vertexColor[v] = COLOR.WHITE;
                bfs(v);
            }
        }

        System.out.println("----------------------------------");
        System.out.println("Color of vertices are:");
        System.out.println(Arrays.toString(vertexColor));
        System.out.println("----------------------------------");

        return bipartite;
    }

    private COLOR complement(final int u) {
        switch (vertexColor[u]) {
            case BLACK:
                return COLOR.WHITE;
            case WHITE:
                return COLOR.BLACK;
            case UNCOLORED:
                throw new IllegalArgumentException("Uncolored vertex received");
            default:
                throw new IllegalArgumentException("Unhandled color");
        }
    }

    @Override
    void processVertexEarly(int u) {

    }

    @Override
    void processEdge(int u, int v) {
        // u -> v
        if (vertexColor[u] == vertexColor[v]) {
            // Means the graph cannot be bipartite since `u` will never be uncolored
            System.out.printf("[WARN] %d -> %d prevents this graph from being bipartite\n", u, v);
            bipartite = false;
        } else {
            vertexColor[v] = complement(u);
        }
    }

    @Override
    void processVertexLate(int v) {

    }

    public static void main(String[] args) {
        final Graph testGraph = GraphLibrary.graph5(true);

        final BipartiteGraph bg = new BipartiteGraph(testGraph);
        System.out.printf("This graph is %s\n", bg.isBipartite() ? "bipartite" : "not bipartite");
    }
}
