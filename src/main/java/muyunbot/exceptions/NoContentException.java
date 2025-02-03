package muyunbot.exceptions;

/**
 * Occurs when task has missing elements.
 */
public class NoContentException extends Exception {
    public NoContentException(String message) {
        super(message);
    }
}
