package org.lahsivjar.graph.dfs;

import org.lahsivjar.graph.Graph;
import org.lahsivjar.graph.GraphLibrary;

/**
 * For a directed graph(DG) any edge can be of 4 type:
 * 1. Tree edge
 * 2. Back edge
 * 3. Forward edge
 * 4. Cross edge
 *
 * NOTE: In an undirected graph for DFS there are only tree edges and back edges.
 */
public class DirectedGraphEdgeClassification extends DFS {

    public DirectedGraphEdgeClassification(Graph g) {
        super(g);
        if (!g.isDirected()) {
            throw new IllegalArgumentException("Only valid for directed graph");
        }
    }

    @Override
    void processVertexEarly(int u) {

    }

    @Override
    void processEdge(int u, int v) {
        // u -> v
        if (parent[v] == u) {
            System.out.printf("[Tree Edge] %d -> %d\n", u, v);
        } else if (!processed[v] && discovered[v]) {
            System.out.printf("[Back Edge] %d -> %d\n", u, v);
        } else if (processed[v]) {
            // Forward edge means that while processing the vertex u, an edge u -> v has been found such that
            // v is processed and is a decendant of u
            if (entryTime[u] < entryTime[v]) {
                System.out.printf("[Forward Edge] %d -> %d\n", u, v);
            }
            // A cross edge means that while processing a vertex u, an edge u -> v has been found such that
            // v is a different node that is not related to u (completely different node)
            // Note that in this case if edge u -> v is reversed it becomes a tree edge
            else if (entryTime[u] > entryTime[v]) {
                System.out.printf("[Cross Edge] %d -> %d\n", u, v);
            }
        }
    }

    @Override
    void processVertexLate(int u) {

    }

    public static void main(String[] args) {
        final Graph testGraph = GraphLibrary.graph5(true);

        final DirectedGraphEdgeClassification cycle = new DirectedGraphEdgeClassification(testGraph);
        cycle.dfs(0);
    }
}
