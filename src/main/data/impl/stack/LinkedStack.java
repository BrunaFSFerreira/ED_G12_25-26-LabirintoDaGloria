package main.data.impl.stack;

import main.data.impl.list.LinearNode;

/**
 * Linked implementation of a stack.
 * @param <T> the type of elements held in this stack
 */
public class LinkedStack<T> {
    /** The top node of the stack */
    private LinearNode<T> top;
    /** The number of elements in the stack */
    private int size;

    /**
     * Creates an empty stack
     */
    public LinkedStack() {
        top = null;
        size = 0;
    }

    /**
     * Adds the specified element to the top of this stack
     * @param element the element to be added to the top of this stack
     */
    public void push(T element) {
        LinearNode<T> newNode = new LinearNode<>(element);
        newNode.setNext(top);
        top = newNode;
        size++;
    }

    /**
     * Removes the element at the top of this stack and returns a reference to it.
     * @return the element at the top of this stack
     */
    public T pop() {
        if (isEmpty()) {
            throw new RuntimeException("main.java.impl.Stack empty");
        }
        T result = top.getElement();
        top = top.getNext();
        size--;
        return result;
    }

    /**
     * Returns a reference to the element at the top of this stack.
     * The element is not removed from the stack.
     * @return the element at the top of this stack
     */
    public T peek() {
        if (isEmpty()) {
            System.out.println("The stack is empty");
            return null;
        }
        return top.getElement();
    }

    /**
     * Returns true if this stack contains no elements.
     * @return true if this stack contains no elements
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of elements in this stack.
     * @return the integer representation of number of elements in this stack
     */
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        String result = "";

        LinearNode<T> current = top;
        while (current != null) {
            result += current.getElement().toString() + " ";
            current = current.getNext();
        }
        return result;


    }
}
