package muyunbot.tasks;

/**
 * Provides a class for Todo tasks.
 */
public class Todo extends Task {

    private static final String SYMBOL = "T";
    public Todo(String description) {
        super(description);
    }

    /**
     * Constructs a new Todo object
     * @param description description for the new Todo task.
     * @param isDone Status of the new task as completed, or not completed.
     */
    public Todo(String description, boolean isDone) {
        super(description);
        this.isDone = isDone;
    }

    /**
     * Generates a string representation of the task that is stored in the record.txt so that data can be read and
     * parsed easily when reading the file.
     * @return A string of the task that is kept in the record.txt
     */
    public String toObjStr() {
        return SYMBOL + "|" + (this.isDone ? "1" : "0") + "|" + this.description;
    }

    @Override
    public String toString() {
        return "[" + SYMBOL + "]" + super.toString();
    }
}
