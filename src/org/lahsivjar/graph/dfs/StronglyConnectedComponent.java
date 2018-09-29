package org.lahsivjar.graph.dfs;

import org.lahsivjar.graph.Graph;
import org.lahsivjar.graph.GraphLibrary;
import java.util.Arrays;
import java.util.Stack;

/**
 * Two types of connectivity are defined for a directed graph:
 *
 * 1. Strong connectivity: If every vertex is reachable from every other
 * 2. Weak connectivity: If graph is connected by some path (irrespective of direction, so similar to un directed graph)
 *
 * Both can be found in O(V + E). Any graph can be divided into strongly connected components(SCC) i.e. components which
 * are strongly connected. SCCs can be found in linear time by finding loops (back edges) and then considering the whole
 * loop as a vertex and continuing. The process terminates when no directed loop remains and the number of SCCs is the
 * number of remaining vertices.
 *
 * Observe that if we create a DFS tree such that there are only tree edges then it can never have SCC with size greater
 * than 1. So, it is back edge and cross edge (under some circumstances) that create SCCs by re enforcing connections.
 */
public class StronglyConnectedComponent extends DFS {

    // Tracks earliest accessible ancestor for each vertex
    private final int[] earliestAccessibleAncestor;

    // Tracks which scc the vertex is a part of, starting from `0` representing the first scc
    private final int[] scc;

    // To save the vertices while finding cycles so that we can find all vertices belonging to an scc
    private final Stack<Integer> stack;

    private int sccCount;

    public StronglyConnectedComponent(Graph g) {
        super(g);
        if (!graph.isDirected()) {
            throw new IllegalArgumentException("Only for directed graphs");
        }
        this.earliestAccessibleAncestor = new int[g.size()];
        this.scc = new int[g.size()];
        this.stack = new Stack<>();
    }

    public void findScc() {
        this.sccCount = 0;
        for (int i : graph.getVertices()) {
            if (!discovered[i]) {
                dfs(i);
            }
        }

        System.out.println("Number of SCC: " + sccCount);
        System.out.println(Arrays.toString(scc));
    }

    @Override
    void processVertexEarly(int u) {
        // initialize required arrays for vertex u
        earliestAccessibleAncestor[u] = u;
        scc[u] = -1;

        // Push vertex u to stack to track SCC
        stack.push(u);
    }

    @Override
    void processEdge(int u, int v) {
        // u -> v
        // Since directed graph there can be 4 kind of edges:
        // 1. Tree edge
        // 2. Back edge
        // 3. Forward edge
        // 4. Cross edge
        //
        // Forward edge can be ignored since it will never contribute to SCC (think about it)
        // Cross edge can contribute to SCC if for edge u -> v, both u and v doesn't belong to SCC (think about it)
        if (discovered[v] && !processed[v]) {
            // Back edge
            if (entryTime[v] < entryTime[earliestAccessibleAncestor[u]]) {
                earliestAccessibleAncestor[u] = v;
            }
        } else if (processed[v] && entryTime[u] > entryTime[v]) {
            // Cross edge
            // For u -> v, u can never belong to SCC so we just check if v is not a part of SCC
            if (scc[v] == -1) {
                // It can contribute to SCC
                if (entryTime[v] < entryTime[earliestAccessibleAncestor[u]]) {
                    earliestAccessibleAncestor[u] = v;
                }
            }
        }
    }

    @Override
    void processVertexLate(int u) {
        if (earliestAccessibleAncestor[u] == u) {
            // Means this is one of the vertices in the final graph representing 1 scc
            int poppedVertex = -1;
            // Mark all vertices that are part of this SCC
            do {
                poppedVertex = stack.pop();
                scc[poppedVertex] = sccCount;
            } while (poppedVertex != u);
            // Increase scc count
            sccCount++;
        }

        // Update earliest reachable ancestor of parent
        if (parent[u] != -1 && earliestAccessibleAncestor[u] < earliestAccessibleAncestor[parent[u]]) {
            earliestAccessibleAncestor[parent[u]] = earliestAccessibleAncestor[u];
        }
    }

    public static void main(String[] args) {
        final Graph testGraph = GraphLibrary.graph5(true);

        final StronglyConnectedComponent scg = new StronglyConnectedComponent(testGraph);
        scg.findScc();
    }
}
