package org.lahsivjar.graph.dfs;

import org.lahsivjar.graph.Graph;

import java.util.Arrays;

/**
 * Implementation of depth first search algorithm. By implementing the abstract method in various way the algorithm
 * can be applied to various use cases. The power of dfs comes from the following points:
 *
 * 1. It classifies vertex based on entry and exit times
 * 2. It classifies edges based on tree edge or back edge
 *
 * Types of edges in a graph:
 * There are a total of 4 types of edges possible in a graph:
 * 1. Tree edge
 * 2. Back edge
 * 3. Forward edge
 * 4. Cross edge
 *
 * NOTE: In DFS for undirected graph every edge is either tree edge and back edge. In a DFS for directed graph all
 * types of edges can be encountered.
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

    /**
     * Will print a path from u -> v in the DFS tree of the graph
     * @param u the source vertex
     * @param v the destination vertex
     */
    public void printPath(final int u, final int v) {
        if (u == v || parent[v] == -1) {
            System.out.print(v + " ");
            return;
        }

        printPath(u, parent[v]);
        System.out.print(v + " ");
    }

    // Is v an ancestor of u
    public boolean isAncestor(final int u, final int v) {
        // v -> () -> () ... -> u
        // ---entry(v)----entry(u)----exit(u)----exit(v)--> t
        final int entryDiff = entryTime[u] - entryTime[v];
        final int exitDiff = exitTime[v] - exitTime[u];

        return entryDiff > 0 && exitDiff > 0;
    }

    public int getNumberOfDescendants(final int u) {
        return (exitTime[u] - entryTime[u]) / 2;
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
                        // 1. v is undiscovered (which is taken care of in the actual dfs code)
                        // 2. v is unprocessed and parent of u is not equals to v
                        //
                        // Note that in below condition for directed graph processed[v] will never be true
                        // in-fact since DFS for undirected graph only has tree edge and back edge, processed
                        // array can be removed for this case
                || (discovered[v] && !processed[v] && parent[u] != v);
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
