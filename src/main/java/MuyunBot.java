import java.util.Arrays;
import java.util.Scanner;

public class MuyunBot {

    private static Task[] taskList = new Task[100];
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
     * Creates a task with input as description. Then add it to the taskList;
     * @param input description of the new task
     */
    private static void addTask(String input) {
        Task newTask = new Task(input);
        taskList[listLength] = newTask;
        listLength++;
        display(PrintStyle.indent("added: " + input));
    }

    /**
     * Marks the task with index ind in taskList as done. Displays a message after marking as done.
     * @param ind index to be marked as done in the taskList.
     */
    private static void markAsDone(int ind) {
        taskList[ind - 1].markAsDone();
        String text = PrintStyle.indent("well done, 1 task down! \n");
        text += PrintStyle.indent(taskList[ind - 1].toString());
        display(text);
    }

    /**
     * Marks the task with index ind in taskList as undone. Displays a message after that.
     * @param ind index to be marked as undone in the taskList.
     */
    private static void markAsUndone(int ind) {
        taskList[ind - 1].markNotDone();
        String text = PrintStyle.indent("oops, seems like this task isn't done yet... \n");
        text += PrintStyle.indent(taskList[ind - 1].toString());
        display(text);
    }

    /**
     * display the whole taskList;
     */
    private static void showList() {
        StringBuilder listContent = new StringBuilder();
        for (int i = 0; i < listLength; i++) {
            listContent.append(PrintStyle.indent(
                    String.valueOf(i + 1) + ". " + taskList[i].toString()));
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

    public static void main(String[] args) {
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
            }
            else {
                addTask(input);
            }
            input = scanner.nextLine();
            comms = input.split(" ");
        }
        quit();

    }

}

