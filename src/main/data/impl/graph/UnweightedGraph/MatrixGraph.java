package main.data.impl.graph.UnweightedGraph;


import main.data.adt.GraphADT;
import main.data.impl.list.ArrayUnorderedList;
import main.data.impl.queue.LinkedQueue;
import main.data.impl.stack.LinkedStack;

import java.util.Iterator;

/**
 * Graph represents an adjacency matrix implementation of a graph.
 * @param <T> the type of elements stored in the graph
 */
public class MatrixGraph<T> implements GraphADT<T> {
    /** Default capacity of the graph */
    protected final int DEFAULT_CAPACITY = 10;
    /** Number of vertices in the graph */
    protected int numVertices;
    /** Adjacency matrix to represent the graph */
    protected boolean[][] adjMatrix;
    /** Array to hold the vertices of the graph */
    protected T[] vertices;

    /**
     * Creates an empty graph
     */
    public MatrixGraph() {
        numVertices = 0;
        this.adjMatrix = new boolean[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        this.vertices = (T[]) new Object[DEFAULT_CAPACITY];
    }

    /**
     * Adds a vertex to the graph, expanding the capacity of the gaph if necessary
     * It also associates an object with the vertex
     * @param vertex the vertex to add to the graph
     */
    @Override
    public void addVertex(T vertex) {
        if (numVertices == vertices.length) {
            expandCapacity();
        }
        vertices[numVertices] = vertex;
        for (int i = 0; i <= numVertices; i++) {
            adjMatrix[numVertices][i] = false;
            adjMatrix[i][numVertices] = false;
        }
        numVertices++;
        
    }

    /**
     * Expands the capacity of the graph
     */
    private void expandCapacity() {
        int newCapacity = vertices.length * 2;
        T[] newVertices = (T[]) new Object[newCapacity];
        for (int i = 0; i < vertices.length; i++) {
            newVertices[i] = vertices[i];
        }
        boolean[][] newAdj = new boolean[newCapacity][newCapacity];
        for (int i = 0; i < adjMatrix.length; i++) {
            for (int j = 0; j < adjMatrix.length; j++) {
                newAdj[i][j] = adjMatrix[i][j];
            }
        }
        vertices = newVertices;
        adjMatrix = newAdj;
    }

    /**
     * Removes a vertex from the graph
     * @param vertex the vertex to remove from the graph
     */
    @Override
    public void removeVertex(T vertex) {
        int idx = getIndex(vertex);
        if (!indexIsValid(idx)) {
            return;
        }

        for (int i = idx; i < numVertices - 1; i++) {
            vertices[i] = vertices[i + 1];
        }
        vertices[numVertices - 1] = null;

        for (int i = idx; i < numVertices - 1; i++) {
            for (int j = 0; j < numVertices; j++) {
                adjMatrix[i][j] = adjMatrix[i + 1][j];
            }
        }

        for (int j = idx; j < numVertices - 1; j++) {
            for (int i = 0; i < numVertices - 1; i++) {
                adjMatrix[i][j] = adjMatrix[i][j + 1];
            }
        }

        for (int i = 0; i < numVertices; i++) {
            adjMatrix[numVertices - 1][i] = false;
            adjMatrix[i][numVertices - 1] = false;
        }

        numVertices--;
    }

    /**
     * Inserts an edge between two vertices of the graph
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
    */
    @Override
    public void addEdge(T vertex1, T vertex2, double weight) {
        addEdge(getIndex(vertex1), getIndex(vertex2));
    }

    /**
     * Returns the index of the given vertex
     * @param vertex1 the vertex to find the index of
     * @return the index of the vertex, or -1 if not found
     */
    private int getIndex(T vertex1) {
        for (int i = 0; i < numVertices; i++) {
            if (vertices[i] == null) {
                if (vertex1 == null) {
                    return i;
                }
            } else if (vertices[i].equals(vertex1)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Inserts an edge between two vertices of the graph
     * @param index1 the first vertex
     * @param index2 the second vertex
     */
    public void addEdge(int index1, int index2) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = true;
            adjMatrix[index2][index1] = true;
        }
    }

    /**
     * Checks if the given index is valid
     * @param index1 the index to check
     * @return true if the index is valid, false otherwise
     */
    private boolean indexIsValid(int index1) {
        return index1 >= 0 && index1 < numVertices;
    }

    /**
     * Removes an edge between two vertices of the graph
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
    */
    @Override
    public void removeEdge(T vertex1, T vertex2) {
        int index1 = getIndex(vertex1);
        int index2 = getIndex(vertex2);
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = false;
            adjMatrix[index2][index1] = false;
        }
    }

    /**
     * Returns an iterator that performs a breadth first search traversal starting at the given vertex
     * @param startVertex the index to begin the search from
     * @return an iterator that performs a breadth first search traversal
     */
    @Override
    public Iterator<T> interatorBFS(T startVertex) {
        Integer x;
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<Integer>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();

        int startIndex = getIndex(startVertex);
        if (!indexIsValid(startIndex)) {
            return resultList.iterator();
        }
        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }
        
        traversalQueue.enqueue(startIndex);
        visited[startIndex] = true;
        
        while (!traversalQueue.isEmpty()) {
            x = traversalQueue.dequeue();
            resultList.addToRear(vertices[x]);
            /**
             * Find all vertices adjacent to x that have not been visited and enqueue them up
             */
            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[x][i] && !visited[i]) {
                    traversalQueue.enqueue(i);
                    visited[i] = true;
                }
            }
        }
        return resultList.iterator();
    }

    /**
     * Returns an iterator that performs a depth first search traversal starting at the given vertex
     * @param startVertex the index to begin the search transversal from
     * @return an iterator that performs a depth first search traversal
     */
    @Override
    public Iterator<T> interatorDFS(T startVertex) {
        Integer x;
        boolean found;
        LinkedStack<Integer> traversalStack = new LinkedStack<Integer>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();
        boolean[] visited = new boolean[numVertices];

        int startIndex = getIndex(startVertex);
        if(!indexIsValid(startIndex)) {
            return resultList.iterator();
        }
        
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }
        
        traversalStack.push(startIndex);
        resultList.addToRear(vertices[startIndex]);
        visited[startIndex] = true;
        
        while (!traversalStack.isEmpty()) {
            x = traversalStack.peek();
            found = false;
            /**
             * Find a vertex adjacent to x that has not been visited and push it on the stack
             */
            for (int i = 0; i < numVertices && !found; i++) {
                if (adjMatrix[x][i] && !visited[i]) {
                    traversalStack.push(i);
                    resultList.addToRear(vertices[i]);
                    visited[i] = true;
                    found = true;
                }
            }
            if (!found && !traversalStack.isEmpty()) {
                traversalStack.pop();
            }
        }
        return resultList.iterator();
    }

    /**
     * Returns an iterator that finds the shortest path between two vertices using BFS
     * @param startVertex the starting vertex
     * @param targetVertex the target vertex
     * @return an iterator that contains the shortest path between the two vertices
     */
    @Override
    public Iterator<T> interatorShortestPath(T startVertex, T targetVertex) {
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();
        int startIndex = getIndex(startVertex);
        int targetIndex = getIndex(targetVertex);
        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex)) {
            return resultList.iterator();
        }

        LinkedQueue<Integer> q = new LinkedQueue<Integer>();
        boolean[] visited = new boolean[numVertices];
        int[] pred = new int[numVertices];
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
            pred[i] = -1;
        }

        visited[startIndex] = true;
        q.enqueue(startIndex);
        boolean found = false;

        while (!q.isEmpty() && !found) {
            int u = q.dequeue();
            for (int v = 0; v < numVertices; v++) {
                if (adjMatrix[u][v] && !visited[v]) {
                    visited[v] = true;
                    pred[v] = u;
                    q.enqueue(v);
                    if (v == targetIndex) {
                        found = true;
                        break;
                    }
                }
            }
        }

        if (!visited[targetIndex]) {
            return resultList.iterator();
        }

        int crawl = targetIndex;
        ArrayUnorderedList<T> reverse = new ArrayUnorderedList<T>();
        while (crawl != -1) {
            reverse.addToRear(vertices[crawl]);
            crawl = pred[crawl];
        }

        for (T vertex : reverse) {
            resultList.addToFront(vertex);
        }

        return resultList.iterator();
    }

    /**
     * Checks if the graph is empty
     * @return true if the graph is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return numVertices == 0;
    }

    /**
     * Checks if the graph is connected
     * @return true if the graph is connected, false otherwise
     */
    @Override
    public boolean isConnected() {
        return false;
    }

    /**
     * Returns the number of vertices in the graph
     * @return the number of vertices in the graph
     */
    @Override
    public int size() {
        return numVertices;
    }
}
