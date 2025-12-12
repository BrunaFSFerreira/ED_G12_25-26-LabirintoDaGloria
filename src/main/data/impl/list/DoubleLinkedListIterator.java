package main.data.impl.list;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.function.Supplier;

/**
 * Iterator for a double linked list.
 * @param <E> the element type
 */
public class DoubleLinkedListIterator<E> implements Iterator<E> {
    /** The current node in the iteration. */
    private DoubleNode<E> current;
    /** The expected modification count for the list. */
    private final int expextedModCount;
    /** The supplier for the current modification count of the list. */
    private final Supplier<Integer> modCountSupplier;

    /**
     * Creates a new iterator.
     * @param expextedModCount the expected modification count
     * @param modCountSupplier the supplier for the current modification count
     * @param current the current node
     */
    public DoubleLinkedListIterator(int expextedModCount, Supplier<Integer> modCountSupplier, DoubleNode<E> current) {
        this.expextedModCount = expextedModCount;
        this.modCountSupplier = modCountSupplier;
        this.current = current;
    }

    @Override
    public boolean hasNext() {
        if (expextedModCount != modCountSupplier.get()) {
            throw new ConcurrentModificationException();
        }
        return current != null;
    }

    @Override
    public E next() {
        if (expextedModCount != modCountSupplier.get()) {
            throw new ConcurrentModificationException();
        }
        if (current == null) {
            throw new IllegalStateException("No more elements in the list");
        }
        E elem = current.getElement();
        current = current.getNext();
        return elem;
    }
}
