package org.lahsivjar.graph.dfs;

import org.lahsivjar.graph.Graph;
import org.lahsivjar.graph.GraphLibrary;

import java.util.Stack;

/**
 * Topological sorting for a directed acyclic graph means arranging the nodes in such a way that all edges go from
 * left to right. Every directed acyclic graph has atleast one topological sorting. Applying DFS to a DAG will give
 * a topological sort if vertices are arranged in reverse order from what they are marked as processed. A decendant
 * is always processed before.
 *
 * 1. Tree edge: order of processed vertices for tree edge is always right to left, thus reversing it gives the
 * expected order for topological sort.
 * 2. Back edge: means it is no longer a DAG
 * 3. Forward edge: still obeys the constraints of topological sort
 * 4. Cross edge: from definition source vertex of cross edge will be processed after the destination vertex thus
 * reversing it obeys the constraint of topological sorting
 */
public class TopologicalSorting extends DFS {

    private final Stack<Integer> stack;

    public TopologicalSorting(Graph g) {
        super(g);
        if (!g.isDirected()) {
            throw new IllegalArgumentException("Only valid for directed graph");
        }
        stack = new Stack<>();
    }

    public void sort() {
        for (int i : graph.getVertices()) {
            if (!discovered[i]) {
                super.dfs(i);
            }
        }
        System.out.println("Topological sort is:");
        while (!stack.isEmpty()) {
            System.out.print(stack.pop() + " ");
        }
        System.out.println();
    }

    @Override
    void processVertexEarly(int u) {

    }

    @Override
    void processEdge(int u, int v) {
        // u -> v
        if (!processed[v] && discovered[v]) {
            // back edge
            throw new IllegalStateException("The graph has a cycle thus no topological sort exists");
        }
    }

    @Override
    void processVertexLate(int u) {
        stack.push(u);
    }

    public static void main(String[] args) {
        final Graph testGraph = GraphLibrary.graph6(true);

        final TopologicalSorting tsort = new TopologicalSorting(testGraph);
        tsort.sort();
    }


}
