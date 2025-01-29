package classes;

import classes.Task;

public class Todo extends Task {

    private static final String SYMBOL = "T";
    public Todo(String description) {
        super(description);
    }

    public Todo (String description, boolean isDone) {
        super(description);
        this.isDone = isDone;
    }

    @Override
    public String toString() {
        return "[" + SYMBOL + "]" + super.toString();
    }

    public String toObjStr() {
        return SYMBOL + "|" + (this.isDone ? "1" : "0") + "|" + this.description;
    }
}