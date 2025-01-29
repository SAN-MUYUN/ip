package classes;

import classes.Task;

public class Event extends Task {
    private String startTime;
    private String endTime;
    private static final String SYMBOL = "E";

    public Event(String description, String startTime, String endTime, boolean isDone) {
        super(description);
        this.startTime = startTime.trim();
        this.endTime = endTime.trim();
        this.isDone = isDone;
    }

    public String toObjStr() {
        return (SYMBOL + "|" + (this.isDone ? "1" : "0") + "|" + this.description
                + "|" + this.startTime + "|" + this.endTime);
    }

    @Override
    public String toString() {
        return "[" + SYMBOL + "]" + super.toString()
                + " (from: " + this.startTime
                + " to: " + this.endTime + ")";
    }


}
