package muyunbot.exceptions;

/**
 * Occurs when trying to access a property of a Task that does not exist.
 */
public class NoTaskPropertyException extends Exception {
    public NoTaskPropertyException(String message) {
        super(message);
    }
}
