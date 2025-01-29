package classes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    public static void display(String x) {
        // display x in proper style;
        String text = PrintStyle.dashedLines()
                + x
                + PrintStyle.dashedLines();
        System.out.println(text);
    }

    public static String displayDate(LocalDate date) {
        return(date.getDayOfWeek().toString().substring(0, 3) + " "
                + date.format(DateTimeFormatter.ofPattern("MMM d yyyy")));
    }
}

