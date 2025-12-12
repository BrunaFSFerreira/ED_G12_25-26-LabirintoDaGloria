package main.data.impl.list;

import main.data.adt.OrderedListADT;

import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * A linked-list implementation of an ordered list ADT
 * @param <T> the type of elements in the list
 */
public class LinkedOrderedList<T> extends LinkedList<T> implements OrderedListADT<T> {

    /** Constructor Methods */
    public LinkedOrderedList() {
        super();
    }

    /**
     * Adds the specified element to this list at the proper location
     * @param element the element to be added to this list
     */
    @Override
    public void add(T element) {
        LinearNode<T> newNode = new LinearNode<>(element);

        if (head == null) {
            head = tail = newNode;
        } else {
            LinearNode<T> current = head;
            LinearNode<T> previous = null;

            while (current != null && ((Comparable<T>) current.getElement()).compareTo(element) < 0) {
                previous = current;
                current = current.getNext();
            }

            if (previous == null) {
                newNode.setNext(head);
                head = newNode;
            } else if (current == null) {
                tail.setNext(newNode);
                tail = newNode;
            } else {
                previous.setNext(newNode);
                newNode.setNext(current);
            }
        }

        count++;
    }

    /**
     * Performs the given action for each element of the Iterable until all elements have been processed or the action throws an exception.
     * @param action The action to be performed for each element
     */
    @Override
    public void forEach(Consumer<? super T> action) {
        super.forEach(action);
    }

    /**
     * Creates a Spliterator over the elements in this list
     * @return a Spliterator over the elements in this list
     */
    @Override
    public Spliterator<T> spliterator() {
        return super.spliterator();
    }

}
