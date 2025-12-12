package main.data.execption;

/**
 * Exception thrown when an expected element is not found in a data structure.
 */
public class ElementNotFoundExecption extends Exception {

    /** Constructs a new ElementNotFoundExecption with a default message. */
    public ElementNotFoundExecption() {
        super("Element not found in the data structure.");
    }

    /**
     * Constructs a new ElementNotFoundExecption with the specified detail message.
     * @param message the detail message
     */
    public ElementNotFoundExecption(String message) {
        super(message);
    }
}