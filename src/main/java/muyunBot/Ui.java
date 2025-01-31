package muyunBot;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Ui {

    /**
     * Draws a separation line for formatting when displayed on UI.
     * @return A string of indented "_" line with a newline character at the end.
     */
    public static String dashedLines() {
        return ("    __________"+"\n");
    }

    /**
     * Adds indentation to a string for formatting.
     * @param x input string
     * @return Indented string for formatting
     */
    public static String indent(String x) {
        return ("    " + x + "\n");
    }

    /**
     * Displays the content of a string onto the commandline in a formatted style.
     * @param x A string content to be displayed on the UI.
     */
    public static void display(String x) {
        // display x in proper style;
        String text = Ui.dashedLines()
                + x
                + Ui.dashedLines();
        System.out.println(text);
    }

    /**
     * Converts a LocalDate into a string representation of date to be displayed on the screen.
     * @param date LocalDate representation of the user input date.
     * @return A string representation of date to be displayed on the UI.
     */
    public static String displayDate(LocalDate date) {
        return(date.getDayOfWeek().toString().substring(0, 3) + " "
                + date.format(DateTimeFormatter.ofPattern("MMM d yyyy")));
    }
}

