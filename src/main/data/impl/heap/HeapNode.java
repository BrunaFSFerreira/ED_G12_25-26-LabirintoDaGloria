package main.data.impl.heap;

import main.data.impl.tree.BinaryTreeNode;

/**
 * A node for a heap data structure
 * @param <T> the type of elements contained within the heap node
 */
public class HeapNode<T> extends BinaryTreeNode<T> {
    /** The left child of this heap node */
    public HeapNode<T> left;
    /** The right child of this heap node */
    public HeapNode<T> right;
    /** The data contained within this heap node */
    public T element;
    /** The parent of this heap node */
    protected HeapNode<T> parent;

    /**
     * Creates a heap node with the specified data.
     * @param obj the data to be contained within the new heap nodes
     */
    HeapNode(T obj) {
        super(obj);
        parent = null;
    }
}
