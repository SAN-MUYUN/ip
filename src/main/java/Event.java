public class Event extends Task{
    private String startTime;
    private String endTime;
    private static final String SYMBOL = "E";

    public Event(String description, String startTime, String endTime) {
        super(description);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "[" + SYMBOL + "]" + super.toString()
                + " (from: " + this.startTime
                + "to: " + this.endTime + "\b)";
    }


}
