package muyunbot;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import muyunbot.exceptions.NoContentException;
import muyunbot.tasks.Deadline;
import muyunbot.tasks.Event;
import muyunbot.tasks.Todo;



/**
 * Parses the user inputs.
 */
public class Parser {

    /**
     * parse the dateString input from user into a LocalDate object
     * @param dateString user input for datetime
     * @return a LocalDate object by parsing the dateString
     * @throws DateTimeParseException if dateString is invalid
     */
    public static LocalDate parseDate(String dateString) throws DateTimeParseException {
        LocalDate date = LocalDate.parse(dateString);
        return date;
    }

    /**
     * Creates a new Todo instance using the input from commandline.
     * @param inputArr array of input received from commandline by the user.
     * @return Returns the new MuyunBot.classes.Todo created.
     */
    public Todo createTodo(String[] inputArr) throws NoContentException {
        assert inputArr[0].equalsIgnoreCase("todo") : "creating wrong task";
        StringBuilder descr = new StringBuilder();
        if (inputArr.length == 1) {
            throw new NoContentException("I see you want to do something, "
                    + "what exactly is this task about?");
        }
        for (int i = 1; i < inputArr.length; i++) {
            descr.append(inputArr[i]);
            descr.append(" ");
        }
        return new Todo(descr.toString(), false);

    }

    /**
     * Creates a new Deadline instance using the input from commandline.
     * @param inputArr array of input received from commandline by the user.
     * @return Returns the new MuyunBot.classes.Deadline created.
     */
    public Deadline createDeadline(String[] inputArr) throws NoContentException {
        assert inputArr[0].equalsIgnoreCase("deadline") : "creating wrong task";
        if (inputArr.length == 1) {
            throw new NoContentException("I see a deadline is approaching, "
                    + "what exactly is this task about?");
        }
        int counter = 1;
        StringBuilder description = new StringBuilder();
        StringBuilder deadLine = new StringBuilder();
        while (counter < inputArr.length && !inputArr[counter].equals("/by")) {
            description.append(inputArr[counter] + " ");
            counter++;
        }
        if (counter == inputArr.length) {
            throw new NoContentException("When is this deadline? "
                    + "add a deadline using format: deadline {task} /by {deadline date and time}");
        }
        counter++;
        while (counter < inputArr.length) {
            deadLine.append(inputArr[counter] + " ");
            counter++;
        }
        if (description.length() == 0 || deadLine.length() == 0) {
            throw new NoContentException("Seems like task description or deadline is missing...");
        }
        return new Deadline(description.toString(), deadLine.toString(), false);
    }

    /**
     * Creates an Events object.
     * @param inputArr Parsed user input.
     * @return A new Event object built from info in the inputArr.
     * @throws NoContentException If elements are missing from user input.
     */
    public Event createEvent(String[] inputArr) throws NoContentException {
        assert inputArr[0].equalsIgnoreCase("event") : "creating wrong task";
        if (inputArr.length == 1) {
            throw new NoContentException("I see you want to create an event, "
                    + "what exactly is this task about?");
        }
        int counter = 1;
        StringBuilder description = new StringBuilder();
        StringBuilder startTime = new StringBuilder();
        StringBuilder endTime = new StringBuilder();
        while (counter != inputArr.length && !inputArr[counter].equals("/from")) {
            description.append(inputArr[counter] + " ");
            counter++;
        }
        counter++;
        while (counter != inputArr.length && !inputArr[counter].equals("/to")) {
            startTime.append(inputArr[counter] + " ");
            counter++;
        }
        counter++;
        while (counter < inputArr.length) {
            endTime.append(inputArr[counter] + " ");
            counter++;
        }
        if (description.length() == 0 || startTime.length() == 0 || endTime.length() == 0) {
            throw new NoContentException("description or start time or end time for event is missing"
                    + "please follow template: event {event description} /from {start time} /to {end time}");
        }
        return new Event(description.toString(), startTime.toString(), endTime.toString(), false);
    }

    /**
     * Parses the input into a string array.
     * @param input User input.
     * @return String array after splitting the input.
     */
    public String[] generateCommand(String input) throws NoContentException {
        String[] commandArray = input.split(" ");
        if (commandArray.length == 0) {
            throw new NoContentException("Sorry I didn't hear you clearly, please input a valid command");
        }
        return commandArray;
    }

    /**
     * Parses the input into a list of strings to facilitate update
     * @param inputArr
     * @return An ArrayList of 2 elements string arrays, the first element is the property label of a task to be
     *     updated, second element is the new content to update.
     * @throws NoContentException
     */
    public ArrayList<String[]> parseUpdate(String[] inputArr) throws NoContentException {
        ArrayList<String[]> listOfUpdates = new ArrayList<>();
        String label = "description";
        StringBuilder content = new StringBuilder();
        if (inputArr.length <= 2) {
            throw new NoContentException("Missing content for update");
        }
        for (int i = 2; i < inputArr.length; i++) {
            // Labels the property to be updated.
            if (inputArr[i].equals("/by")) {
                appendUpdate(listOfUpdates, label, content);
                label = "deadline";
                content = new StringBuilder();
                continue;
            } else if (inputArr[i].equals("/from")) {
                appendUpdate(listOfUpdates, label, content);
                label = "startTime";
                content = new StringBuilder();
                continue;
            } else if (inputArr[i].equals("/to")) {
                appendUpdate(listOfUpdates, label, content);
                label = "endTime";
                content = new StringBuilder();
                continue;
            } else if (inputArr[i].split("")[0].equals("/")) {
                throw new NoContentException("wrong update command");
            }
            content.append(" " + inputArr[i]);

        }
        appendUpdate(listOfUpdates, label, content);
        return listOfUpdates;
    }
    private void appendUpdate(ArrayList<String[]> listOfUpdates, String label, StringBuilder content)
            throws NoContentException {
        if (!content.isEmpty()) {
            listOfUpdates.add(new String[]{label, content.toString().trim()});
        }
    }


}
