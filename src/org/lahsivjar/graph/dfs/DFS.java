package org.lahsivjar.graph.dfs;

import org.lahsivjar.graph.Graph;

import java.util.Arrays;

/**
 * Implementation of depth first search algorithm. By implementing the abstract method in various way the algorithm
 * can be applied to various use cases. The power of dfs comes from the following points:
 *
 * 1. It classifies vertex based on entry and exit times
 * 2. It classifies edges based on tree edge or back edge
 */
abstract class DFS {
    private int time;

    final Graph graph;
    final boolean[] discovered;
    final boolean[] processed;
    final int[] parent;
    final int[] entryTime;
    final int[] exitTime;

    public DFS(Graph g) {
        this.graph = g;
        this.discovered = new boolean[g.size()];
        this.processed = new boolean[g.size()];
        this.parent = new int[g.size()];
        this.entryTime = new int[g.size()];
        this.exitTime = new int[g.size()];
        this.time = 0;

        Arrays.fill(parent, -1);
    }

    public void dfs(final int s) {
        discovered[s] = true;
        entryTime[s] = ++time;

        processVertexEarly(s);

        for (int u : graph.getEdges(s)) {
            // s -> u
            if (!discovered[u]) {
                discovered[u] = true;
                parent[u] = s;
                processEdge(s, u);
                dfs(u);
            } else if (needToProcessEdge(s, u)) {
                processEdge(s, u);
            }
        }

        processVertexLate(s);

        exitTime[s] = ++time;
        processed[s] = true;
    }

    /*
     * For an undirected graph a edge x -- y will be processed two times. Once when x is discovered and again when
     * y is discovered. This method will return true if the edge is discovered the first time.
     */
    private boolean needToProcessEdge(final int u, final int v) {
        // u -> v
        return
                // If graph is directed then it must be the first encounter for edge u -> v
                (graph.isDirected())
                // For an undirected graph an edge u -> v is discovered first time under following circumstances:
                        // 1. v is undiscovered (which is taken care of in the actual dfs code
                        // 2. v is processed (which means the edge must be discovered as v -> u while processing v)
                        // 3. v is unprocessed and parent of u is not equals to v
                || (!processed[v] && parent[u] != v);
    }

    public void printState() {
        System.out.println("----------------------------------");
        System.out.println("Parent array:");
        System.out.println(Arrays.toString(parent));
        System.out.println("----------------------------------");
        System.out.println("Entry time:");
        System.out.println(Arrays.toString(entryTime));
        System.out.println("----------------------------------");
        System.out.println("Exit time:");
        System.out.println(Arrays.toString(exitTime));
        System.out.println("----------------------------------");
    }

    abstract void processVertexEarly(final int u);

    abstract void processEdge(final int u, final int v);

    abstract void processVertexLate(final int u);
}
