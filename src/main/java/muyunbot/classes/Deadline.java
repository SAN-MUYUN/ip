package muyunbot.classes;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import muyunbot.Parser;

/**
 * Provides the model for tasks with deadline
 */
public class Deadline extends Task {
    private static final String SYMBOL = "D";
    private LocalDate deadLine;

    /**
     * Constructs a deadline object.
     * @param description Description of the task.
     * @param deadLine Deadline of the task.
     * @param isDone Whether the task is done.
     * @throws DateTimeParseException If the deadline passed in is in the wrong format and cannot be parsed.
     */
    public Deadline(String description, String deadLine, boolean isDone) throws DateTimeParseException {
        super(description);
        this.isDone = isDone;
        this.deadLine = Parser.parseDate(deadLine.trim());
    }

    /**
     * Generates a string representation of the task that is stored in the record.txt so that data can be read and
     * parsed easily when reading the file.
     * @return A string of the task that is kept in the record.txt
     */
    public String toObjStr() {
        return (SYMBOL + "|" + (this.isDone ? "1" : "0") + "|" + this.description
            + "|" + this.deadLine);
    }

    @Override
    public String toString() {
        return "[" + SYMBOL + "]" + super.toString() + " (by: " + this.ui.displayDate(this.deadLine) + ")";
    }
}
