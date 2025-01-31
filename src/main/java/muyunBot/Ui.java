package muyunBot;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Ui {
    public static String dashedLines() {
        return ("    __________"+"\n");
    }

    /**
     * Adds indentation to a string for formatting.
     * @param x input string
     * @return Indented string for formatting
     */
    public static String indent(String text) {
        return ("    " + text + "\n");
    }

    public static void display(String text) {
        // display x in proper style;
        String text = Ui.dashedLines()
                + text
                + Ui.dashedLines();
        System.out.println(text);
    }

    public static String displayDate(LocalDate date) {
        return(date.getDayOfWeek().toString().substring(0, 3) + " "
                + date.format(DateTimeFormatter.ofPattern("MMM d yyyy")));
    }
}

