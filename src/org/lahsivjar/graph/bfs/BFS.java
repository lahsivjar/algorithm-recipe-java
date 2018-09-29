package org.lahsivjar.graph.bfs;

import org.lahsivjar.graph.Graph;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Implementation of breadth first search algorithm.
 * NOTE: This is a generic implementation. For a specific application of bfs some of the fields may not be required.
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

    private void init() {
        this.queue.clear();
        Arrays.fill(parent, -1);
        Arrays.fill(discovered, false);
        Arrays.fill(processed, false);
    }

    public void bfs(final int s) {
        init();
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

    /**
     * Prints the path from source vertex (for which dfs was executed) to destination vertex taken as parameter
     * @param destination the destination vertex
     */
    public void printPath(final int destination) {
        final int parentNode = parent[destination];
        if (parentNode == -1) {
            System.out.printf("Head: %d", destination);
            return;
        }
        printPath(parentNode);
        System.out.printf(" -> %d", destination);
    }

    public void printParent() {
        System.out.println("----------------------------------");
        System.out.println("Parent array:");
        System.out.println(Arrays.toString(parent));
        System.out.println("----------------------------------");
    }

    abstract void processVertexEarly(int u);

    abstract void processEdge(int u, int v);

    abstract void processVertexLate(int v);
}
