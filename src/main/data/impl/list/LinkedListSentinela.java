package main.data.impl.list;

/**
 * A linked list implementation with a sentinel node
 * @param <T> the type of elements in the list
 */
public class LinkedListSentinela<T> {
    /** Sentinel node */
    private LinearNode<T> sentinel;

    /** Constructor Methods */
    public LinkedListSentinela() {
        sentinel = new LinearNode<T>(null);
    }

    /**
     * Adds the specified element to the end of the list
     * @param element the element to be added to the list
     */
    public void add(T element) {
        LinearNode<T> newNode = new LinearNode<T>(element);
        LinearNode<T> current = sentinel;

        while (current.getNext() != null) {
            current = current.getNext();
        }
        current.setNext(newNode);

    }

    /**
     * Removes the specified element from the list
     * @param element the element to be removed from the list
     * @return the removed element
     * @throws Exception if the element is not found
     */
    public T remove(T element) throws Exception {
        System.out.print("[EX2] Antes do remove: ");
        printPointers();

        LinearNode<T> previous = sentinel;
        LinearNode<T> current = sentinel.getNext();

        while (current != null) {
            if (element.equals(current.getElement())) {
                previous.setNext(current.getNext());
                System.out.print("[EX2] Depois do remove: ");
                printPointers();
                return current.getElement();
            }
            previous = current;
            current = current.getNext();
        }
        throw new Exception("Elemento nao encontrado");

    }

    /**
     * Prints the pointers of the list for debugging
     */
    private void printPointers() {
        LinearNode<T> current = sentinel;
        System.out.print("sentinela -> ");
        while (current.getNext() != null) {
            current = current.getNext();
            System.out.print(current.getElement() + " -> ");
        }
        System.out.println("null");
    }

    /**
     * Returns a string representation of the list
     * @return a string representation of the list
     */
    @Override
    public String toString() {
        String result = "";
        LinearNode<T> current = sentinel.getNext();
        while (current != null) {
            result += current.getElement().toString() + " ";
            current = current.getNext();
        }
        return result.trim();
    }
}
