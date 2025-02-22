package muyunbot.exceptions;

/**
 * Occurs when end time in Event comes before the start time.
 */
public class TimeTravelException extends Exception {
    public TimeTravelException(String message) {
        super(message);
    }
}
