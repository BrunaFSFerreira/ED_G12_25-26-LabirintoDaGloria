package main.data.impl.tree;

/**
 * BinaryTreeNode represents a node in a binary tree with a left and right child.
 * @param <T> the type of element contained in the node
 */
public class BinaryTreeNode<T> {
    /** The element contained in this node */
    protected T element;
    /** The left and right children of this node */
    protected BinaryTreeNode<T> left, right;

    /**
     * Creates a binary tree node with the specified data.
     * @param obj the element that will become a part of the new tree node
     */
    protected BinaryTreeNode(T obj) {
        element = obj;
        left = right = null;
    }

    /**
     * Returns the number of nom-null children of this node.
     * This method may be able to be written more efficiently.
     * @return the integer number of non-null children of this node
     */
    public int numChildren() {
        int children = 0;

        if (left != null)
            children = 1 + left.numChildren();
        if (right != null)
            children = children + 1 + right.numChildren();

        return children;
    }

    /**
     * Returns the element of this node.
     * @return the element contained within this node
     */
    public T getElement() {
        return element;
    }

    /**
     * Sets the element of this node.
     * @param element the element to set
     */
    public void setElement(T element) {
        this.element = element;
    }
}
