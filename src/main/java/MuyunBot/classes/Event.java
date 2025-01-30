package MuyunBot.classes;

import MuyunBot.Ui;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    private LocalDate startTime;
    private LocalDate endTime;
    private static final String SYMBOL = "E";

    public Event(String description, String startTime, String endTime, boolean isDone) throws DateTimeParseException {
        super(description);
        this.startTime = Parser.parseDate(startTime.trim());
        this.endTime = Parser.parseDate(endTime.trim());
        this.isDone = isDone;
    }

    public String toObjStr() {
        return (SYMBOL + "|" + (this.isDone ? "1" : "0") + "|" + this.description
                + "|" + this.startTime + "|" + this.endTime);
    }

    @Override
    public String toString() {
        return "[" + SYMBOL + "]" + super.toString()
                + " (from: " + Ui.displayDate(this.startTime)
                + " to: " + Ui.displayDate(this.endTime) + ")";
    }


}
