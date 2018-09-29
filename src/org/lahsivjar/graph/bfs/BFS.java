package org.lahsivjar.graph.bfs;

import org.lahsivjar.graph.Graph;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Implementation of breadth first search algorithm
 */
abstract class BFS {

    private final Queue<Integer> queue;

    final Graph graph;
    final int[] parent;
    final boolean[] discovered;
    final boolean[] processed;

    public BFS(final Graph graph) {
        this.graph = graph;
        this.queue = new LinkedList<>();
        this.parent = new int[graph.size()];
        this.discovered = new boolean[graph.size()];
        this.processed = new boolean[graph.size()];
    }

    public void bfs(final int s) {
        queue.add(s);
        discovered[s] = true;

        while (!queue.isEmpty()) {
            final int u = queue.poll();
            processVertexEarly(u);

            for (final int v : graph.getEdges(u)) {
                // u -> v
                if (!discovered[v]) {
                    parent[v] = u;
                    discovered[v] = true;
                    processEdge(u, v);
                    queue.add(v);
                } else if (
                        // If graph is directed then all edges are ok
                        graph.isDirected()
                        // If graph is undirected then every edge is processed twice so need to check for this
                        || parent[u] != v) {
                    processEdge(u, v);
                }
            }

            processVertexLate(u);
            processed[u] = true;
        }
    }

    abstract void processVertexEarly(int u);

    abstract void processEdge(int u, int v);

    abstract void processVertexLate(int v);
}
