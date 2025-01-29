package classes;

import java.time.DateTimeException;
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


    public String toObjStr() {
        return (SYMBOL + "|" + (this.isDone ? "1" : "0") + "|" + this.description
            + "|" + this.deadLine);
    }

    @Override
    public String toString() {
        return "[" + SYMBOL + "]" + super.toString() + " (by: " + PrintStyle.displayDate(this.deadLine) + ")";
    }
}
