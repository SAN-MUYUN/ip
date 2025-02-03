package muyunbot.classes;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import muyunbot.Parser;
import muyunbot.Ui;


/**
 * Represents a Task with start time and end time.
 */
public class Event extends Task {

    private static final String SYMBOL = "E";
    private LocalDate startTime;
    private LocalDate endTime;
    /**
     * Constructs an event object.
     * @param description
     * @param startTime Start time of the event.
     * @param endTime End time of the event.
     * @param isDone whether the task is done.
     * @throws DateTimeParseException If time passed is in the wrong format and cannot be parsed properly.
     */
    public Event(String description, String startTime, String endTime, boolean isDone) throws DateTimeParseException {
        super(description);
        this.startTime = Parser.parseDate(startTime.trim());
        this.endTime = Parser.parseDate(endTime.trim());
        this.isDone = isDone;
    }

    /**
     * Generates a string representation of the task that is stored in the record.txt so that data can be read and
     * parsed easily when reading the file.
     * @return A string of the task that is kept in the record.txt
     */
    public String toObjStr() {
        return (SYMBOL + "|" + (this.isDone ? "1" : "0") + "|" + this.description
                + "|" + this.startTime + "|" + this.endTime);
    }

    @Override
    public String toString() {
        return "[" + SYMBOL + "]" + super.toString()
                + " (from: " + Ui.displayDate(this.startTime)
                + " to: " + Ui.displayDate(this.endTime) + ")";
    }


}
