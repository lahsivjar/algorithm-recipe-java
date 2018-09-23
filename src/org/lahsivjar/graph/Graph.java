package org.lahsivjar.graph;

import java.util.LinkedList;

public interface Graph {
    int size();

    boolean isDirected();

    void addEdge(int src, int dest);

    LinkedList<Integer> getEdges(int s);

    void printGraph();

}
