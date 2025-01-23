package classes;

import classes.Task;

public class Deadline extends Task {
    private static final String SYMBOL = "D";
    private String deadLine;
    public Deadline(String description, String deadLine) {
        super(description);
        this.deadLine = deadLine.trim();
    }

    @Override
    public String toString() {
        return "[" + SYMBOL + "]" + super.toString() + " (by: " + this.deadLine + ")";
    }
}
