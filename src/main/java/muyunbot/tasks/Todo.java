package muyunbot.tasks;

import muyunbot.exceptions.NoTaskPropertyException;

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

    private String updateDescription(String newDescr) {
        this.description = newDescr;
        return (ui.indent("content updated: ")
                + ui.indent(this.toString()));
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
    public void update(String[] updateInfo) throws NoTaskPropertyException {
        assert updateInfo.length == 2 : "wrong updateInfo format";
        if (updateInfo[0].equals("description")) {
            this.description = updateInfo[1];
        } else {
            throw new NoTaskPropertyException("No Such Attribute: " + updateInfo[0] + " in Todo");
        }
    }

    @Override
    public String toString() {
        return "[" + SYMBOL + "]" + super.toString();
    }
}
