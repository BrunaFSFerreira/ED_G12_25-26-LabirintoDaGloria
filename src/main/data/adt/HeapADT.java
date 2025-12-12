package main.data.adt;

import main.data.execption.EmptyCollectionExecption;

/**
 * HeapADT defines the interface to a heap
 * @param <T> the type of elements stored in the heap
 */
public interface HeapADT<T> extends BinaryTreeADT<T> {

    /**
     * Adds the specified element to this heap
     * @param obj the element to be added to this heap
     */
    public void addElement(T obj);

    /**
     * Removes element with the lowest value from this heap
     * @return the element with the lowest value from this heap
     * @throws EmptyCollectionExecption if the heap is empty
     */
    public T removeMin() throws EmptyCollectionExecption;

    /**
     * Returns a reference to the element with the lowest value in this heap
     * @return a reference to the element with the lowest value in this heap
     */
    public T findMin();
}
