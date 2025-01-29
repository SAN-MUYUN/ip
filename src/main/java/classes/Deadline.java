package classes;

import classes.Task;

public class Deadline extends Task {
    private static final String SYMBOL = "D";
    private String deadLine;
    public Deadline(String description, String deadLine, boolean isDone) {
        super(description);
        this.deadLine = deadLine.trim();
        this.isDone = isDone;
    }


    public String toObjStr() {
        return (SYMBOL + "|" + (this.isDone ? "1" : "0") + "|" + this.description
            + "|" + this.deadLine);
    }

    @Override
    public String toString() {
        return "[" + SYMBOL + "]" + super.toString() + " (by: " + this.deadLine + ")";
    }
}
