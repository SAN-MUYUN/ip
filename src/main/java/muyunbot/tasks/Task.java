package muyunbot.tasks;

import muyunbot.Ui;
import muyunbot.exceptions.NoTaskPropertyException;

import java.time.format.DateTimeParseException;

/**
 * Provides an abstraction for all the tasks managed by the bot.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;
    protected Ui ui;

    /**
     * Constructs a simple task object with only description and isDone status.
     * @param description Description of the task.
     */
    public Task(String description) {
        this.description = description.trim();
        this.isDone = false;
        this.ui = new Ui();
    }

    /**
     * Sets the status of a task as completed.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Sets the status of a task as incomplete.
     */
    public void markNotDone() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }

    public String getStatusIcon() {
        // mark done task with X
        return (isDone ? "X" : " ");
    }

    public String describe() {
        return this.description;
    }

    public abstract String toObjStr();

    /**
     * Updates the description of the task to a new description.
     * @param updateInfo String array of 2 elements, first element contains the property to be updated
     *                   the second element contains the information to be updated.
     * @return return a string output when after updating the description.
     */
    public abstract void update(String[] updateInfo) throws NoTaskPropertyException;

}
