package main.data.impl.list;

import main.data.adt.OrderedListADT;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * An array-based implementation of an ordered list ADT
 * @param <T> the type of elements in the list
 */
public class ArrayOrderedList<T> extends ArrayList<T> implements OrderedListADT<T> {
    /** Constructor Methods */
    public ArrayOrderedList() {
        super();
    }

    /**
     * Constructor with initial capacity
     * @param initialCapacity the initial capacity of the list
     */
    public ArrayOrderedList(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Adds the specified element to this list at the proper location
     * @param element the element to be added to this list
     */
    @Override
    public void add(T element) {
        if(size() == list.length) {
            extandCapacity();
        }
        int i = 0;
        while (i < rear && ((Comparable<T>) list[i]).compareTo(element) < 0) {
            i++;
        }

        for (int j = rear; j > i; j--) {
            list[j] = list[j - 1];
        }

        list[i] = element;
        ++rear;
        ++modCount;
    }

    /**
     * Returns an iterator for the list
     * @return an iterator for the list
     */
    @Override
    public Iterator<T> iterator() {
        return new BasicIterator();
    }

    /**
     * A basic iterator for the ordered list
     */
    private class BasicIterator implements Iterator<T> {
        /** The current position in the list */
        private int current;
        /** The expected modification count */
        private int expectedModCount;
        /** Flag to indicate if it's okay to remove an element */
        private boolean okToRemove = false;

        /** Constructor Method */
        public BasicIterator() {
            current = 0;
            expectedModCount = modCount;
        }

        public boolean hasNext() {
            return current < rear;
        }

        public T next() throws IllegalStateException {
            if (current >= rear) {
                throw new IllegalStateException("No more elements in the list");
            }

            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException("The list has been modified since the iterator was created");
            }

            okToRemove = true;
            expectedModCount = modCount;
            return list[current++];
        }

        /**
         * Removes the last element returned by the iterator
         */
        public void remove() {
            if (!okToRemove) {
                throw new IllegalStateException("Cannot remove element before calling next()");
            }

            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException("The list has been modified since the iterator was created");
            }

            ArrayOrderedList.this.remove(list[--current]);
            expectedModCount = modCount;
            okToRemove = false;
        }
    }
}
