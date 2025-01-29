package classes;

import classes.Deadline;
import classes.Event;
import classes.Todo;
import exceptions.NoContentException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {

    /**
     * Creates a new Todo instance using the input from commandline.
     * @param inputArr array of input received from commandline by the user.
     * @return Returns the new classes.Todo created.
     */
    public Todo createTodo(String[] inputArr) throws NoContentException {
        StringBuilder descr = new StringBuilder();
        if (inputArr.length == 1) {
            throw new NoContentException("I see you want to do something, " +
                    "what exactly is this task about?");
        }
        for(int i = 1; i < inputArr.length; i++) {
            descr.append(inputArr[i]);
            descr.append(" ");
        }
        return new Todo(descr.toString(), false);

    }

    /**
     * Creates a new Deadline instance using the input from commandline.
     * @param inputArr array of input received from commandline by the user.
     * @return Returns the new classes.Deadline created.
     */
    public Deadline createDeadline(String[] inputArr) throws NoContentException {
        if (inputArr.length == 1) {
            throw new NoContentException("I see a deadline is approaching, " +
                    "what exactly is this task about?");
        }
        int counter = 1;
        StringBuilder description = new StringBuilder();
        StringBuilder deadLine = new StringBuilder();
        while (counter < inputArr.length && !inputArr[counter].equals("/by")) {
            description.append(inputArr[counter] + " ");
            counter++;
        }
        if (counter == inputArr.length) {
            throw new NoContentException("When is this deadline? " +
                    "add a deadline using format: deadline {task} /by {deadline date and time}");
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

    public Event createEvent(String[] inputArr) throws NoContentException {
        if (inputArr.length == 1) {
            throw new NoContentException("I see you want to create an event, " +
                    "what exactly is this task about?");
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
        while(counter < inputArr.length) {
            endTime.append(inputArr[counter] + " ");
            counter++;
        }
        if (description.length() == 0 || startTime.length() == 0 || endTime.length() == 0) {
            throw new NoContentException("description or start time or end time for event is missing" +
                    "please follow template: event {event description} /from {start time} /to {end time}");
        }
        return new Event(description.toString(), startTime.toString(), endTime.toString(), false);
    }

    public static LocalDate parseDate(String dateString) throws DateTimeParseException{
        LocalDate date = LocalDate.parse(dateString);
        return date;
    }


}
