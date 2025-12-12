package main.data.impl.list;

import main.data.adt.UnorderedListADT;

/**
 * Implementation of a doubly linked and unordered list.
 * @param <E> the type of elements stored in the list
 */
public class DoubleLinkedUnorderedList<E> extends DoubleLinkedList<E> implements UnorderedListADT<E> {

    /** Default constructor */
    public DoubleLinkedUnorderedList() {
        super();
    }

    @Override
    public void addToFront(E element) {
        DoubleNode<E> newNode = new DoubleNode<>(element);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            newNode.setNext(head);
            head.setPrevious(newNode);
            head = newNode;
        }
        count++;
        modCount++;
    }

    @Override
    public void addToRear(E element) {
        DoubleNode<E> newNode = new DoubleNode<>(element);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.setNext(newNode);
            newNode.setPrevious(tail);
            tail = newNode;
        }
        count++;
        modCount++;
    }

    @Override
    public void addAfter(E element, E target) {
        DoubleNode<E> newNode = new DoubleNode<>(element);
        DoubleNode<E> current = head;

        while (current != null) {
            E el = current.getElement();
            if ((target == null && el == null) || (target != null && target.equals(el))) {
                newNode.setNext(current.getNext());
                newNode.setPrevious(current);
                if (current.getNext() != null) {
                    current.getNext().setPrevious(newNode);
                } else {
                    tail = newNode;
                }
                current.setNext(newNode);
                count++;
                modCount++;
                return;
            }
            current = current.getNext();
        }
        throw new IllegalArgumentException("Target element not found in the list.");

    }
}
