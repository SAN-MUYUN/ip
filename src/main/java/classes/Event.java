package classes;

import classes.Task;

public class Event extends Task {
    private String startTime;
    private String endTime;
    private static final String SYMBOL = "E";

    public Event(String description, String startTime, String endTime) {
        super(description);
        this.startTime = startTime.trim();
        this.endTime = endTime.trim();
    }

    @Override
    public String toString() {
        return "[" + SYMBOL + "]" + super.toString()
                + " (from: " + this.startTime
                + " to: " + this.endTime + ")";
    }


}
