package main.data.execption;

/**
 * Exception thrown when an operation is attempted on an empty collection.
 */
public class EmptyCollectionExecption extends Exception {
    /** Constructs a new EmptyCollectionExecption with a default message */
    public EmptyCollectionExecption() {
        super("The collection is empty.");
    }

    /**
     * Constructs a new EmptyCollectionExecption with the specified message
     * @param message the detail message
     */
    public EmptyCollectionExecption(String message) {
        super(message);
    }
}