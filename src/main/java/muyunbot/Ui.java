package muyunbot;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;



/**
 * Handles user interface and user interactions.
 */

public class Ui {


    /**
     * Draws a separation line for formatting when displayed on UI.
     * @return A string of indented "_" line with a newline character at the end.
     */
    public String dashedLines() {
        return ("    __________" + "\n");
    }

    /**
     * Adds indentation to a string for formatting.
     * @param text input string
     * @return Indented string for formatting
     */
    public String indent(String text) {
        return ("    " + text + "\n");
    }

    /**
     * Displays the content of a string onto the commandline in a formatted style.
     * @param text A string content to be displayed on the UI.
     */
    public String display(String text) {
        // display x in proper style;
        assert text != null : "text is null";
        String textToDisplay = this.dashedLines()
                + text
                + this.dashedLines();
        System.out.println(textToDisplay);
        return textToDisplay;

    }

    /**
     * Converts a LocalDate into a string representation of date to be displayed on the screen.
     * @param date LocalDate representation of the user input date.
     * @return A string representation of date to be displayed on the UI.
     */
    public String displayDate(LocalDate date) {
        return (date.getDayOfWeek().toString().substring(0, 3) + " "
                + date.format(DateTimeFormatter.ofPattern("MMM d yyyy")));
    }
}

