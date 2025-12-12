package main.data.impl.list;

import main.data.adt.UnorderedListADT;

import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Implementing an unordered list using a linked list.
 * @param <T> the type of the elements in the list
 */
public class LinkedUnorderedList<T> extends LinkedList<T> implements UnorderedListADT<T> {

    /** Default constructor */
    public LinkedUnorderedList() {
        super();
    }

    @Override
    public void addToFront(T element) {
        LinearNode<T> newNode = new LinearNode<>(element);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            newNode.setNext(head);
            head = newNode;
        }
        count++;
    }

    @Override
    public void addToRear(T element) {
        LinearNode<T> newNode = new LinearNode<>(element);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.setNext(newNode);
            tail = newNode;
        }
        count++;
    }

    @Override
    public void addAfter(T element, T target) {
        LinearNode<T> newNode = new LinearNode<>(element);
        LinearNode<T> current = head;

        while (current != null) {
            T el = current.getElement();
            if ((target == null && el == null) || (target != null && target.equals(el))) {
                newNode.setNext(current.getNext());
                current.setNext(newNode);
                if (current == tail) {
                    tail = newNode;
                }
                count++;
                return;
            }
            current = current.getNext();
        }

        throw new IllegalArgumentException("Target element not found in the list.");
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        super.forEach(action);
    }

    @Override
    public Spliterator<T> spliterator() {
        return super.spliterator();
    }
}