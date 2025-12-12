package main.data.impl.queue;

import main.data.adt.QueueADT;

/**
 * CircularArrayQueue represents a circular array implementation of a queue
 * @param <T> the type of elements in the queue
 */
public class CircularArrayQueue<T> implements QueueADT<T> {
    /** Default capacity of the queue */
    private static final int DEFAULT_CAPACITY = 10;
    /** Instance variables */
    private int front, rear, count;
    /** Array to hold the elements of the queue */
    private T[] queue;

    /**
     * Constructor Methods
     * @param capacity the initial capacity of the queue
     */
    public CircularArrayQueue(int capacity) {
        front = rear = 0;
        count = 0;
        queue = (T[]) new Object[capacity];
    }

    /**
     * Creates a circular array queue with default capacity
     */
    public CircularArrayQueue() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Adds the specified element to the rear of the queue,
     * expanding the capacity of the queue if necessary
     * @param element the element to be added to the rear of the queue
     */
    public void enqueue(T element) {
        if (count == queue.length) {
            expandCapacity();
        }
        queue[rear] = element;
        rear = (rear + 1) % queue.length;
        count++;
    }

    /**
     * Removes and returns the element at the front of the queue
     * @return the element at the front of the queue
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Fila está vazia");
        }
        T result = queue[front];
        queue[front] = null;
        front = (front + 1) % queue.length;
        count--;
        return result;
    }

    /**
     * Returns the element at the front of the queue without removing it
     * @return the element at the front of the queue
     */
    public T first() {
        if (isEmpty()) {
            throw new IllegalStateException("Fila está vazia");
        }
        return queue[front];
    }

    /**
     * Expands the capacity of the queue
     */
    private void expandCapacity() {
        int newCapacity = queue.length * 2;
        T[] newQueue = (T[]) new Object[newCapacity];
        for (int i = 0; i < count; i++) {
            newQueue[i] = queue[(front + i) % queue.length];
        }
        queue = newQueue;
        front = 0;
        rear = count;
    }

    /**
     * Returns true if the queue is empty, false otherwise
     * @return true if the queue is empty, false otherwise
     */
    public boolean isEmpty() {
        return count == 0;
    }

    /**
     * Returns the number of elements in the queue
     * @return the number of elements in the queue
     */
    public int size() {
        return count;
    }

    /**
     * Returns a string representation of the queue
     * @return a string representation of the queue
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < count; i++) {
            if (i > 0) sb.append(", ");
            sb.append(queue[(front + i) % queue.length]);
        }
        sb.append("]");
        return sb.toString();
    }
}
