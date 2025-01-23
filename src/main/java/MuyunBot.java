import java.util.ArrayList;
import java.util.Scanner;

public class MuyunBot {

    private static final Task[] TASKLIST = new Task[100];
    private static int listLength = 0;

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
        TASKLIST[listLength] = newTask;
        listLength++;
        String text = PrintStyle.indent("new task is here!")
                + PrintStyle.indent("added: " + newTask.toString())
                + PrintStyle.indent("now we have " + listLength + " tasks in the list");
        display(text);
    }

    /**
     * Marks the task with index ind in TASKLIST as done. Displays a message after marking as done.
     * @param ind index to be marked as done in the TASKLIST.
     */
    private static void markAsDone(int ind) {
        TASKLIST[ind - 1].markAsDone();
        String text = PrintStyle.indent("well done, 1 task down! \n");
        text += PrintStyle.indent(TASKLIST[ind - 1].toString());
        display(text);
    }

    /**
     * Marks the task with index ind in TASKLIST as undone. Displays a message after that.
     * @param ind index to be marked as undone in the TASKLIST.
     */
    private static void markAsUndone(int ind) {
        TASKLIST[ind - 1].markNotDone();
        String text = PrintStyle.indent("oops, seems like this task isn't done yet... \n");
        text += PrintStyle.indent(TASKLIST[ind - 1].toString());
        display(text);
    }

    /**
     * display the whole TASKLIST;
     */
    private static void showList() {
        StringBuilder listContent = new StringBuilder();
        for (int i = 0; i < listLength; i++) {
            listContent.append(PrintStyle.indent(
                    String.valueOf(i + 1) + ". " + TASKLIST[i].toString()));
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
     * Creates a new Todo instance using the input from commandline.
     * @param inputArr array of input received from commandline by the user.
     * @return Returns the new Todo created.
     */
    private static Todo createTodo(String[] inputArr) {
        StringBuilder descr = new StringBuilder();
        for(int i = 1; i < inputArr.length; i++) {
            descr.append(inputArr[i]);
            descr.append(" ");
        }
        return new Todo(descr.toString());

    }
    
    private static Deadline createDeadline (String[] inputArr) {
        int counter = 1;
        StringBuilder description = new StringBuilder();
        StringBuilder deadLine = new StringBuilder();
        while (!inputArr[counter].equals("/by")) {
            description.append(inputArr[counter] + " ");
            counter++;
        }
        counter++;
        while (counter < inputArr.length) {
            deadLine.append(inputArr[counter] + " ");
            counter++;
        }
        return new Deadline(description.toString(), deadLine.toString());
    }

    private static Event createEvent(String[] inputArr) {
        int counter = 1;
        StringBuilder description = new StringBuilder();
        StringBuilder startTime = new StringBuilder();
        StringBuilder endTime = new StringBuilder();
        while (!inputArr[counter].equals("/from")) {
            description.append(inputArr[counter] + " ");
            counter++;
        }
        counter++;
        while (!inputArr[counter].equals("/to")) {
            startTime.append(inputArr[counter] + " ");
            counter++;
        }
        counter++;
        while(counter < inputArr.length) {
            endTime.append(inputArr[counter] + " ");
            counter++;
        }
        return new Event(description.toString(), startTime.toString(), endTime.toString());
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
                int ind = Integer.valueOf(comms[1]);
                markAsDone(ind);
            } else if (comms[0].equals("unmark")) {
                int ind = Integer.valueOf(comms[1]);
                markAsUndone(ind);
            } else if (comms[0].equals("todo")) {
                addTask(createTodo(comms));
            } else if (comms[0].equals("deadline")) {
                addTask(createDeadline(comms));
            } else if (comms[0].equals("event")) {
                addTask(createEvent(comms));
            } else {
                display(PrintStyle.indent("Sorry, No such command"));
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

