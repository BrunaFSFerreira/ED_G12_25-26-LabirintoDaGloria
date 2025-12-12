package main.data.impl.list;

import main.data.adt.OrderedListADT;

/**
 * A doubly linked implementation of an ordered list ADT
 * @param <E> the type of elements in the list
 */
public class DoubleLinkedOrderedList<E> extends DoubleLinkedList<E> implements OrderedListADT<E> {
    /** Constructor Methods */
    public DoubleLinkedOrderedList() {
        super();
    }

    /**
     * Adds the specified element to this list at the proper location
     * @param element the element to be added to this list
     */
    @Override
    public void add(E element) {
        DoubleNode<E> newNode = new DoubleNode<>(element);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            DoubleNode<E> current = head;
            DoubleNode<E> previous = null;
            while (current != null && ((Comparable<E>) current.getElement()).compareTo(element) < 0) {
                previous = current;
                current = current.getNext();
            }
            if (previous == null) {
                newNode.setNext(head);
                head.setPrevious(newNode);
                head = newNode;
            } else if (current == null) {
                previous.setNext(newNode);
                newNode.setPrevious(previous);
                tail = newNode;
            } else {
                previous.setNext(newNode);
                newNode.setPrevious(previous);
                newNode.setNext(current);
                current.setPrevious(newNode);
            }
        }
        count++;
        modCount++;
    }
}
