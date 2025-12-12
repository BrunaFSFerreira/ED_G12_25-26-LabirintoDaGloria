package main.data.impl.list;

import main.data.adt.ListADT;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Array-based implementation of a list.
 * @param <T> the type of elements in the list
 */
public class ArrayList<T> implements ListADT<T> {
    /** The array to hold the list elements. */
    protected T[] list;
    /** The index of the rear of the list.*/
    protected int rear;
    /** The default capacity of the list. */
    private static final int DEFAULT_CAPACITY = 10;
    /** The modification count for the list. */
    protected int modCount;

    /** Creates an empty list with the default capacity. */
    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Creates an empty list with the specified initial capacity.
     * @param initialCapacity the initial capacity of the list
     */
    public ArrayList(int initialCapacity) {
        list = (T[]) new Object[initialCapacity];
        rear = 0;
        modCount = 0;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Lista vazia");
        }
        T remove = list[0];
        for (int i = 0; i < rear - 1; i++) {
            list[i - 1] = list[i];
        }
        list[--rear] = null;
        ++modCount;
        return remove;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Lista vazia");
        }
        T remove = list[--rear];
        list[rear] = null;
        ++modCount;
        return remove;
    }

    /**
     * Finds the index of the specified element in the list.
     * @param element the element to find
     * @return the index of the element, or -1 if not found
     */
    public int find(T element) {
        for (int i = 0; i < rear; i++) {
            if (list[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public T remove(T element) {
        if (isEmpty()) {
            throw new NoSuchElementException("Lista vazia");
        }
        int index = find(element);
        if (index != -1) {
            T remove = list[index];
            for (int i = index; i < rear - 1; i++) {
                list[i] = list[i + 1];
            }
            list[--rear] = null;
            ++modCount;
            return remove;
        }
        return null;
    }



    @Override
    public T first() {
        if (isEmpty()) {
            throw new NoSuchElementException("Lista vazia");
        }
        if (rear > 0) {
            return list[0];
        }
        return null;
    }

    @Override
    public T last() {
        if (isEmpty()) {
            throw new NoSuchElementException("Lista vazia");
        }
        if (rear > 0) {
            return list[rear - 1];
        }
        return null;
    }

    @Override
    public boolean contains(T target) {
        for (int i = 0; i < rear; i++) {
            if (list[i].equals(target)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        if (rear == 0) {
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return rear;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < rear; i++) {
            if (i > 0) sb.append(", ");
            sb.append(list[i]);
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Extends the capacity of the list by doubling its size.
     */
    public void extandCapacity() {
        T[] newList = (T[]) new Object[list.length * 2];
        for (int i = 0; i < list.length; i++) {
            newList[i] = list[i];
        }
        list = newList;
    }
}
