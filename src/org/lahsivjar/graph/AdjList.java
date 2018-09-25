package org.lahsivjar.graph;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AdjList implements Graph {

    final List<LinkedList<Integer>> graphRep;
    final int nodeCount;
    final boolean directed;

    public AdjList(int n, boolean directed) {
        graphRep = new ArrayList<>(n);
        nodeCount = n;
        this.directed = directed;

        for (int i = 0; i < n; i++) {
            graphRep.add(new LinkedList<>());
        }
    }

    @Override
    public int size() {
        return nodeCount;
    }

    @Override
    public boolean isDirected() {
        return this.directed;
    }

    @Override
    public void addEdge(int src, int dest) {
        graphRep.get(src).add(dest);
        if (!directed)
            graphRep.get(dest).add(src);
    }

    @Override
    public LinkedList<Integer> getEdges(int s) {
        return graphRep.get(s);
    }

    @Override
    public List<Integer> getVertices() {
        return IntStream.range(0, nodeCount)
                .boxed()
                .collect(Collectors.toList());
    }

    @Override
    public void printGraph() {
        for (int j = 0; j < graphRep.size(); j++) {
            System.out.print("head -> " + j);
            LinkedList<Integer> l = graphRep.get(j);
            for (Integer i : l) {
                System.out.print(" -> " + i);
            }
            System.out.print("\n");
        }
    }
}
