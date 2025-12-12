package main.data.impl.queue;

/**
 * PriorityQueueNode represents a node in a priority queue.
 * @param <T> the type of element contained in the node
 */
public class PriorityQueueNode<T> implements Comparable<PriorityQueueNode<T>> {
    /** Static variable to keep track of the order of insertion */
    private static int nextorder = 0;
    /** Priority of the node */
    private int priority;
    /** Order of insertion of the node */
    private int order;
    /** Element contained in the node */
    private T element;

    /**
     * Creates a new PriorityQueueNode with the specified data.
     * @param obj the element of the new priority queue node
     * @param prio the integer priority of the new queue node
     */
    public PriorityQueueNode(T obj, int prio) {
        this.element = obj;
        this.priority = prio;
        this.order = nextorder;
        nextorder++;
    }

    /**
     * Returns the element of this priority queue node.
     * @return the element contained within this node
     */
    public T getElement() {
        return element;
    }

    /**
     * Returns the priority value for this node
     * @return the integer priority of this node
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Returns the order for this node
     * @return the integer order of this node
     */
    public int getOrder() {
        return order;
    }

    /**
     * @return a string representation for this node
     */
    public String toString() {
        String temp = (element.toString() + priority + order);
        return temp;
    }

    /**
     * Returns the 1 if the current node has a higher priority than given node and -1 otherwise.
     * @param obj the node to compare to this node
     * @return the integer result of the comparison of the obj node and this node
     */
    @Override
    public int compareTo(PriorityQueueNode obj) {
        int result;
        PriorityQueueNode<T> temp = obj;

        if (priority > temp.getPriority()) {
            result = 1;
        } else if (priority < temp.getPriority()) {
            result = -1;
        } else if (order > temp.getOrder()) {
            result = 1;
        } else {
            result = -1;
        }
        return result;
    }
}
