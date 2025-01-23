
public class PrintStyle {
    public static String dashedLines() {
        return ("    _________"+"\n");
    }

    /**
     * Adds indentation to a string for formatting.
     * @param x input string
     * @return Indented string for formatting
     */
    public static String indent(String x) {
        return ("    " + x + "\n");
    }
}
