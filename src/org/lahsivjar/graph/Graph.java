package org.lahsivjar.graph;

import java.util.LinkedList;
import java.util.List;

public interface Graph {
    int size();

    boolean isDirected();

    void addEdge(int src, int dest);

    LinkedList<Integer> getEdges(int s);

    List<Integer> getVertices();

    void printGraph();

}
