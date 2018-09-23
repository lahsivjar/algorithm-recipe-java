package org.lahsivjar.graph;

public class GraphLibrary {

    public static Graph graph1() {
        // (0) --- (1)
        //  |     / |
        //  |    /  |
        //  |   /   |
        //  |  /    |
        // (2)     (3)

        final Graph graph = new AdjList(4, false);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 2);

        return graph;
    }

    public static Graph graph2() {
        // (0) --- (1) --- (2)
        //  | \     |       |
        //  |  \    |       |
        //  |   \   |       |
        //  |    \  |       |
        //  |     \ |       |
        // (5)     (4) --- (3)

        final Graph graph = new AdjList(6, false);
        graph.addEdge(0, 1);
        graph.addEdge(0, 4);
        graph.addEdge(0, 5);
        graph.addEdge(1, 2);
        graph.addEdge(1, 4);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);

        return graph;
    }

    public static Graph graph3() {
        // (0) --- (1) --- (2)
        //  |
        //  |
        // (3)

        final Graph graph = new AdjList(4, false);
        graph.addEdge(0, 1);
        graph.addEdge(0, 3);
        graph.addEdge(1, 2);

        return graph;
    }
}
