package main.data.impl.queue;

import main.data.adt.QueueADT;
import main.data.impl.list.LinearNode;

/**
 * Linked implementation of a queue data structure.
 * @param <T> the type of elements held in this queue
 */
public class LinkedQueue<T> implements QueueADT<T> {
    /** The front node of the queue */
    private LinearNode<T> front;
    /** The rear node of the queue */
    private LinearNode<T> rear;
    /** The number of elements in the queue */
    private int size;

    /** Creates an empty queue. */
    public LinkedQueue() {
        front = rear = null;
        size = 0;
    }

    @Override
    public void enqueue(T element) {
        LinearNode<T> node = new LinearNode<>(element);
        if (isEmpty()) {
            front = rear = node;
        } else {
            rear.setNext(node);
            rear = node;
        }
        size++;
    }

    @Override
    public T dequeue() {
        if (isEmpty()) {
            return null;
        }
        T elem = front.getElement();
        front = front.getNext();
        size--;
        if (front == null) {
            rear = null;
        }
        return elem;
    }

    @Override
    public T first() {
        return isEmpty() ? null : front.getElement();
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        String result = "";

        LinearNode<T> current = front;
        while (current != null) {
            result += current.getElement().toString() + " ";
            current = current.getNext();
        }
        return result;
    }
}
