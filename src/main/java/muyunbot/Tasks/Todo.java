package muyunbot.Tasks;

public class Todo extends Task {

    private static final String SYMBOL = "T";
    public Todo(String description) {
        super(description);
    }

    /**
     * Creates a Todo object
     * @param description Description of the Todo task
     * @param isDone Whether the task is done.
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