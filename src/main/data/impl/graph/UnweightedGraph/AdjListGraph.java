package main.data.impl.graph.UnweightedGraph;

import main.data.adt.GraphADT;
import main.data.impl.list.ArrayUnorderedList;
import main.data.impl.queue.LinkedQueue;
import main.data.impl.stack.LinkedStack;

import java.util.Iterator;

/**
 * Graph represents an adjacency list implementation of a graph.
 * @param <T> the type of elements stored in the graph
 */
public class AdjListGraph<T> implements GraphADT<T> {
    /** Default capacity of the graph */
    protected final int DEFAULT_CAPACITY = 10;
    /** Number of vertices in the graph */
    protected int numVertices;
    /** Adjacency list representation of the graph */
    protected ArrayUnorderedList<T>[] adjList;
    /** Array of vertices in the graph */
    protected T[] vertices;

    /** Creates an empty graph */
    public AdjListGraph() {
        numVertices = 0;
        this.vertices = (T[]) new Object[DEFAULT_CAPACITY];
        this.adjList = (ArrayUnorderedList<T>[]) new ArrayUnorderedList[DEFAULT_CAPACITY];
        for (int i = 0; i < this.adjList.length; i++) {
            this.adjList[i] = new ArrayUnorderedList<>();
        }
    }

    /**
     * Adds a vertex to the graph, expanding the capacity of the graph if necessary.
     * It also associates an object with the vertex.
     * @param vertex the vertex to add to the graph
     */
    @Override
    public void addVertex(T vertex) {
        if (numVertices == vertices.length) {
            expandCapacity();
        }
        vertices[numVertices] = vertex;
        adjList[numVertices] = new ArrayUnorderedList<>();
        numVertices++;
    }

    /** Expands the capacity of the graph's vertex array and adjacency list */
    private void expandCapacity() {
        int newCapacity = vertices.length * 2;
        T[] newVertices = (T[]) new Object[newCapacity];
        for (int i = 0; i < vertices.length; i++) {
            newVertices[i] = vertices[i];
        }
        vertices = newVertices;

        ArrayUnorderedList<T>[] newAdj = (ArrayUnorderedList<T>[]) new ArrayUnorderedList[newCapacity];
        for (int i = 0; i < newCapacity; i++) {
            if (i < adjList.length && adjList[i] != null) {
                newAdj[i] = adjList[i];
            } else {
                newAdj[i] = new ArrayUnorderedList<>();
            }
        }
        adjList = newAdj;
    }

    /**
     * Removes a vertex from the graph, along with all associated edges.
     * @param vertex the vertex to remove from the graph
     */
    @Override
    public void removeVertex(T vertex) {
        int idx = getIndex(vertex);
        if (!indexIsValid(idx)) {
            return;
        }

        for (int i = 0; i < numVertices; i++) {
            if (i == idx) continue;
            ArrayUnorderedList<T> newList = new ArrayUnorderedList<>();
            Iterator<T> it = adjList[i].iterator();
            while (it.hasNext()) {
                T neighbor = it.next();
                if (neighbor == null) continue;
                if (!neighbor.equals(vertex)) {
                    newList.addToRear(neighbor);
                }
            }
            adjList[i] = newList;
        }

        for (int i = idx; i < numVertices - 1; i++) {
            vertices[i] = vertices[i + 1];
        }
        vertices[numVertices - 1] = null;

        for (int i = idx; i < numVertices - 1; i++) {
            adjList[i] = adjList[i + 1];
        }
        adjList[numVertices - 1] = new ArrayUnorderedList<>();

        numVertices--;
    }


    /**
     * Adds an edge between two vertices in the graph with a specified weight.
     * Since this is an unweighted graph, the weight parameter is ignored.
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @param weight the weight of the edge (ignored in unweighted graph)
     */
    @Override
    public void addEdge(T vertex1, T vertex2, double weight) {
        int index1 = getIndex(vertex1);
        int index2 = getIndex(vertex2);
        addEdge(index1, index2);
    }

    /**
     * Returns the index of a vertex in the graph
     * @param vertex the vertex to find
     * @return the index of the vertex, or -1 if not found
     */
    private int getIndex(T vertex) {
        for (int i = 0; i < numVertices; i++) {
            if (vertices[i] != null && vertices[i].equals(vertex)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Adds an edge between two vertices in the graph using their indices.
     * @param index1 the index of the first vertex
     * @param index2 the index of the second vertex
     */
    public void addEdge(int index1, int index2) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjList[index1].addToRear(vertices[index2]);
            adjList[index2].addToRear(vertices[index1]);
        }
    }

    /**
     * Checks if a given index is valid for the graph
     * @param index1 the index to check
     * @return true if the index is valid, false otherwise
     */
    private boolean indexIsValid(int index1) {
        return index1 >= 0 && index1 < numVertices;
    }

    /**
     * Removes an edge between two vertices in the graph.
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     */
    @Override
    public void removeEdge(T vertex1, T vertex2) {
        int i1 = getIndex(vertex1);
        int i2 = getIndex(vertex2);
        if (!indexIsValid(i1) || !indexIsValid(i2)) return;

        ArrayUnorderedList<T> newList1 = new ArrayUnorderedList<>();
        Iterator<T> interator1 = adjList[i1].iterator();
        while (interator1.hasNext()) {
            T neighbor = interator1.next();
            if (!neighbor.equals(vertex2)) {
                newList1.addToRear(neighbor);
            }
        }
        adjList[i1] = newList1;

        ArrayUnorderedList<T> newList2 = new ArrayUnorderedList<>();
        Iterator<T> interator2 = adjList[i2].iterator();
        while (interator2.hasNext()) {
            T neighbor = interator2.next();
            if (!neighbor.equals(vertex1)) {
                newList2.addToRear(neighbor);
            }
        }
        adjList[i2] = newList2;
    }

    /**
     * Performs a breadth-first search (BFS) traversal starting from a given vertex.
     * @param startVertex the starting vertex for the BFS traversal
     * @return an iterator over the vertices in the order they were visited
     */
    @Override
    public Iterator<T> interatorBFS(T startVertex) {
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
            Integer x = traversalQueue.dequeue();
            resultList.addToRear(vertices[x]);

            Iterator<T> it = adjList[x].iterator();
            while (it.hasNext()) {
                T neighbor = it.next();
                int neighborIndex = getIndex(neighbor);
                if (neighborIndex != -1 && !visited[neighborIndex]) {
                    traversalQueue.enqueue(neighborIndex);
                    visited[neighborIndex] = true;
                }
            }
        }
        return resultList.iterator();
    }

    /**
     * Performs a depth-first search (DFS) traversal starting from a given vertex.
     * @param startVertex the starting vertex for the DFS traversal
     * @return an iterator over the vertices in the order they were visited
     */
    @Override
    public Iterator<T> interatorDFS(T startVertex) {
        Integer x;
        boolean found;
        LinkedStack<Integer> traversalStack = new LinkedStack<Integer>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();
        boolean[] visited = new boolean[numVertices];

        int startIndex = getIndex(startVertex);
        if (!indexIsValid(startIndex)) {
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

            Iterator<T> it = adjList[x].iterator();
            while (it.hasNext() && !found) {
                T neighbor = it.next();
                int neighborIndex = getIndex(neighbor);
                if (neighborIndex != -1 && !visited[neighborIndex]) {
                    traversalStack.push(neighborIndex);
                    resultList.addToRear(vertices[neighborIndex]);
                    visited[neighborIndex] = true;
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
     * Finds the shortest path between two vertices using BFS.
     * @param startVertex the starting vertex
     * @param targetVertex the target vertex
     * @return an iterator over the vertices in the shortest path
     */
    @Override
    public Iterator<T> interatorShortestPath(T startVertex, T targetVertex) {
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();
        int startIndex = getIndex(startVertex);
        int targetIndex = getIndex(targetVertex);
        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex)) {
            return resultList.iterator();
        }

        LinkedQueue<Integer> q = new LinkedQueue<>();
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
            Iterator<T> it = adjList[u].iterator();
            while (it.hasNext() && !found) {
                T neighbor = it.next();
                int v = getIndex(neighbor);
                if (v != -1 && !visited[v]) {
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
        ArrayUnorderedList<T> reverse = new ArrayUnorderedList<>();
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
     * Checks if the graph is empty.
     * @return true if the graph has no vertices, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return numVertices == 0;
    }

    /**
     * Checks if the graph is connected.
     * @return true if the graph is connected, false otherwise
     */
    @Override
    public boolean isConnected() {
        if (isEmpty()) {
            return true;
        }
        Iterator<T> it = interatorBFS(vertices[0]);
        int count = 0;
        while (it.hasNext()) {
            it.next();
            count++;
        }
        return count == numVertices;
    }

    /**
     * Returns the number of vertices in the graph.
     * @return the number of vertices
     */
    @Override
    public int size() {
        return numVertices;
    }

}
