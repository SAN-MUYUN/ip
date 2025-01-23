import classes.Deadline;
import classes.Event;
import classes.Task;
import classes.Todo;
import exceptions.NoContentException;
import exceptions.OutOfListException;

import java.util.ArrayList;
import java.util.Scanner;

public class MuyunBot {

    private static final ArrayList<Task> TASKLIST = new ArrayList<>();

    /**
     * Prints greeting message on commandline
     */
    private static void greet() {
        //display greeting when starting the bot;

        String text = PrintStyle.dashedLines()
                + PrintStyle.indent("Hello! I'm MuyunBot")
                + PrintStyle.indent("What can I do for you?")
                + PrintStyle.dashedLines();
        System.out.println(text);
    }

    /**
     * Ends the program.
     * Displays end message on the commandline
     */
    private static void quit() {
        // display ending message when closing the bot;
        String text = PrintStyle.dashedLines()
                + PrintStyle.indent("Bye! Hope to see you soon!")
                + PrintStyle.dashedLines();
        System.out.println(text);
    }

    /**
     * Creates a task with input as description. Then add it to the TASKLIST.
     * @param newTask New task to be added to the TASKLIST array.
     */
    private static void addTask(Task newTask) {
        TASKLIST.add(newTask);
        String text = PrintStyle.indent("new task is here!")
                + PrintStyle.indent("added: " + newTask.toString())
                + PrintStyle.indent("now we have " + TASKLIST.size() + " tasks in the list");
        display(text);
    }

    /**
     * Marks the task with index ind in TASKLIST as done. Displays a message after marking as done.
     * @param ind index to be marked as done in the TASKLIST.
     * @exception OutOfListException when index is larger than number of Tasks.
     */
    private static void markAsDone(int ind) throws OutOfListException {
        if (ind > TASKLIST.size()) {
            throw new OutOfListException("Task number "
                    + ind
                    + " is out of the list of size "+ TASKLIST.size() +", please double check your index~");
        }
        TASKLIST.get(ind - 1).markAsDone();
        String text = PrintStyle.indent("well done, 1 task down!\n");
        text += PrintStyle.indent(TASKLIST.get(ind - 1).toString());
        display(text);
    }

    /**
     * Marks the task with index ind in TASKLIST as undone. Displays a message after that.
     * @param ind index to be marked as undone in the TASKLIST.
     * @exception OutOfListException when index is larger than number of Tasks.
     */
    private static void markAsUndone(int ind) throws OutOfListException {
        if (ind > TASKLIST.size()) {
            throw new OutOfListException("index "
                    + ind
                    + " is out of the list, please double check your index~");
        }
        TASKLIST.get(ind - 1).markNotDone();
        String text = PrintStyle.indent("oops, seems like this task isn't done yet...\n");
        text += PrintStyle.indent(TASKLIST.get(ind - 1).toString());
        display(text);
    }

    /**
     * display the whole TASKLIST;
     */
    private static void showList() {
        StringBuilder listContent = new StringBuilder();
        for (int i = 0; i < TASKLIST.size(); i++) {
            listContent.append(PrintStyle.indent(
                    String.valueOf(i + 1) + ". " + TASKLIST.get(i).toString()));
        }
        display(listContent.toString());
    }

    private static void display(String x) {
        // display x in proper style;
        String text = PrintStyle.dashedLines()
                + x
                + PrintStyle.dashedLines();
        System.out.println(text);
    }

    /**
     * Creates a new classes.Todo instance using the input from commandline.
     * @param inputArr array of input received from commandline by the user.
     * @return Returns the new classes.Todo created.
     */
    private static Todo createTodo(String[] inputArr) throws NoContentException {
        StringBuilder descr = new StringBuilder();
        if (inputArr.length == 1) {
            throw new NoContentException("I see you want to do something, " +
                    "what exactly is this task about?");
        }
        for(int i = 1; i < inputArr.length; i++) {
            descr.append(inputArr[i]);
            descr.append(" ");
        }
        return new Todo(descr.toString());

    }
    
    private static Deadline createDeadline(String[] inputArr) throws NoContentException {
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
        return new Deadline(description.toString(), deadLine.toString());
    }

    private static Event createEvent(String[] inputArr) throws NoContentException {
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
        return new Event(description.toString(), startTime.toString(), endTime.toString());
    }

    private static void delete(int ind) throws OutOfListException{
        if (ind > TASKLIST.size()) {
            throw new OutOfListException("index " + ind + " is out of the list,"
                    + " please double check your index~");
        }
        Task toBeRemoved = TASKLIST.get(ind - 1);
        TASKLIST.remove(ind - 1);
        String text = PrintStyle.indent("I am removing this task:")
                + PrintStyle.indent(toBeRemoved.toString())
                + PrintStyle.indent("Now there are " + TASKLIST.size() + " tasks in the list");
        display(text);
    }

    private static void run() {
        Scanner scanner = new Scanner(System.in);
        greet();
        String input = scanner.nextLine();
        // Splits the input into a string array comms
        String[] comms = input.split(" ");
        while(!input.equals("bye")) {
            if (input.equals("list")) {
                showList();
            } else if (comms[0].equals("mark")) {
                try {
                    int ind = Integer.valueOf(comms[1]);
                    markAsDone(ind);
                } catch (OutOfListException e) {
                    display(PrintStyle.indent(e.getMessage()));
                } catch (NumberFormatException e) {
                    display(PrintStyle.indent("please enter index right after the command 'mark'"));
                }
            } else if (comms[0].equals("unmark")) {
                try {
                    int ind = Integer.valueOf(comms[1]);
                    markAsUndone(ind);
                } catch (OutOfListException e) {
                    display(PrintStyle.indent(e.getMessage()));
                } catch (NumberFormatException e) {
                    display(PrintStyle.indent("please enter index right after the command 'unmark'"));
                }
            } else if (comms[0].equals("todo")) {
                try {
                    addTask(createTodo(comms));
                } catch (NoContentException e) {
                    display(PrintStyle.indent(e.getMessage()));
                }

            } else if (comms[0].equals("deadline")) {
                try {
                    addTask(createDeadline(comms));
                } catch (NoContentException e) {
                    display(PrintStyle.indent(e.getMessage()));
                }

            } else if (comms[0].equals("event")) {
                try {
                    addTask(createEvent(comms));
                } catch (NoContentException e) {
                    display(PrintStyle.indent(e.getMessage()));
                }
            } else if (comms[0].equals("delete")) {
                try {
                    int ind = Integer.valueOf(comms[1]);
                    delete(ind);
                } catch (OutOfListException e) {
                    display(PrintStyle.indent(e.getMessage()));
                } catch (NumberFormatException e) {
                    display(PrintStyle.indent("please enter index right after command 'delete'"));
                }
            } else {
                display(PrintStyle.indent("Sorry, I have not learnt this command yet :("));
            }
            input = scanner.nextLine();
            comms = input.split(" ");
        }
        quit();
    }

    public static void main(String[] args) {
        run();
    }

}

