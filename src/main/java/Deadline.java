public class Deadline extends Task {
    private static final String SYMBOL = "E";
    private String deadLine;
    public Deadline(String description, String deadLine) {
        super(description);
        this.deadLine = deadLine;
    }

    @Override
    public String toString() {
        return "[" + SYMBOL + "]" + super.toString() + " (by: " + this.deadLine + "\b)";
    }
}
