package classes;

public class Task {
    protected String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description.trim();
        this.isDone = false;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markNotDone() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return "[" + (this.isDone?"X":" ") + "] " + this.description;
    }

    public String getStatusIcon() {
        // mark done task with X
        return (isDone ? "X" : " ");
    }

}
