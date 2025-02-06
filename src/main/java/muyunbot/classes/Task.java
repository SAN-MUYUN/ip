package muyunbot.classes;

import muyunbot.Ui;

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

    public void markAsDone() {
        this.isDone = true;
    }

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

}
