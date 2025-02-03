package muyunbot.exceptions;

/**
 * Occurs when task index requested by user is larger than number of tasks.
 */
public class OutOfListException extends Exception {
    public OutOfListException(String message) {
        super(message);
    }
}
