package muyunBot.classes;

import muyunBot.Parser;
import muyunBot.Ui;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    private static final String SYMBOL = "D";
    private LocalDate deadLine;

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
        return "[" + SYMBOL + "]" + super.toString() + " (by: " + Ui.displayDate(this.deadLine) + ")";
    }
}
