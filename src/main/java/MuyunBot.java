import classes.Parser;
import classes.PrintStyle;
import classes.Task;
import exceptions.NoContentException;
import exceptions.OutOfListException;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.time.DateTimeException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class MuyunBot {

    private ArrayList<Task> TASKLIST;
    private final FileManager fileManager;

    private final Parser parser;

    public MuyunBot() {
        this.TASKLIST = new ArrayList<>();
        this.fileManager = new FileManager();
        this.parser = new Parser();
    }


    /**
     * Prints greeting message on commandline
     */
    private void greet() {
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
    private void quit() {
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
    private void addTask(Task newTask) {
        TASKLIST.add(newTask);
        String text = PrintStyle.indent("new task is here!")
                + PrintStyle.indent("added: " + newTask.toString())
                + PrintStyle.indent("now we have " + TASKLIST.size() + " tasks in the list");
        PrintStyle.display(text);
        this.fileManager.writeFile(newTask.toObjStr());
    }

    /**
     * Marks the task with index ind in TASKLIST as done. Displays a message after marking as done.
     * @param ind index to be marked as done in the TASKLIST.
     * @exception OutOfListException when index is larger than number of Tasks.
     */
    private void markAsDone(int ind) throws OutOfListException {
        if (ind > TASKLIST.size()) {
            throw new OutOfListException("Task number "
                    + ind
                    + " is out of the list of size "+ TASKLIST.size() +", please double check your index~");
        }
        TASKLIST.get(ind - 1).markAsDone();
        String text = PrintStyle.indent("well done, 1 task down!\n");
        text += PrintStyle.indent(TASKLIST.get(ind - 1).toString());
        PrintStyle.display(text);
        this.fileManager.updateFile(TASKLIST);
    }

    /**
     * Marks the task with index ind in TASKLIST as undone. Displays a message after that.
     * @param ind index to be marked as undone in the TASKLIST.
     * @exception OutOfListException when index is larger than number of Tasks.
     */
    private void markAsUndone(int ind) throws OutOfListException {
        if (ind > TASKLIST.size()) {
            throw new OutOfListException("index "
                    + ind
                    + " is out of the list, please double check your index~");
        }
        TASKLIST.get(ind - 1).markNotDone();
        String text = PrintStyle.indent("oops, seems like this task isn't done yet...\n");
        text += PrintStyle.indent(TASKLIST.get(ind - 1).toString());
        PrintStyle.display(text);
        this.fileManager.updateFile(TASKLIST);
    }

    /**
     * display the whole TASKLIST;
     */
    private void showList() {
        StringBuilder listContent = new StringBuilder();
        for (int i = 0; i < TASKLIST.size(); i++) {
            listContent.append(PrintStyle.indent(
                    (i + 1) + ". " + TASKLIST.get(i).toString()));
        }
        PrintStyle.display(listContent.toString());
    }



    private void delete(int ind) throws OutOfListException{
        if (ind > TASKLIST.size()) {
            throw new OutOfListException("index " + ind + " is out of the list,"
                    + " please double check your index~");
        }
        Task toBeRemoved = TASKLIST.get(ind - 1);
        TASKLIST.remove(ind - 1);
        String text = PrintStyle.indent("I am removing this task:")
                + PrintStyle.indent(toBeRemoved.toString())
                + PrintStyle.indent("Now there are " + TASKLIST.size() + " tasks in the list");
        PrintStyle.display(text);
        this.fileManager.updateFile(TASKLIST);
    }

    private void run() {
        Scanner scanner = new Scanner(System.in);
        this.fileManager.initFile();
        try {
            this.TASKLIST = this.fileManager.syncTaskList();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (DateTimeParseException e) {
            PrintStyle.display("File is corrupted, deleting current file and creating new file");
            this.fileManager.rebuildFile();
        }
        greet();
        String input = scanner.nextLine();
        // Splits the input into a string array comms
        String[] comms = input.split(" ");
        while(!input.equals("bye")) {
            if (input.equals("list")) {
                showList();
            } else if (comms[0].equals("mark")) {
                try {
                    int ind = Integer.parseInt(comms[1]);
                    markAsDone(ind);
                } catch (OutOfListException e) {
                    PrintStyle.display(PrintStyle.indent(e.getMessage()));
                } catch (NumberFormatException e) {
                    PrintStyle.display(PrintStyle.indent("please enter index after the command 'mark'"));
                }
            } else if (comms[0].equals("unmark")) {
                try {
                    int ind = Integer.parseInt(comms[1]);
                    markAsUndone(ind);
                } catch (OutOfListException e) {
                    PrintStyle.display(PrintStyle.indent(e.getMessage()));
                } catch (NumberFormatException e) {
                    PrintStyle.display(PrintStyle.indent("please enter index after the command 'unmark'"));
                }
            } else if (comms[0].equals("todo")) {
                try {
                    addTask(parser.createTodo(comms));
                } catch (NoContentException e) {
                    PrintStyle.display(PrintStyle.indent(e.getMessage()));
                }

            } else if (comms[0].equals("deadline")) {
                try {
                    addTask(parser.createDeadline(comms));
                } catch (NoContentException e) {
                    PrintStyle.display(PrintStyle.indent(e.getMessage()));
                } catch (DateTimeParseException e) {
                    PrintStyle.display(PrintStyle.indent("Please input deadline following format yyyy-mm-dd"));
                }

            } else if (comms[0].equals("event")) {
                try {
                    addTask(parser.createEvent(comms));
                } catch (NoContentException e) {
                    PrintStyle.display(PrintStyle.indent(e.getMessage()));
                } catch (DateTimeParseException e) {
                    PrintStyle.display(PrintStyle.indent("Please input deadline following format yyyy-mm-dd"));
                } catch (ArrayIndexOutOfBoundsException e) {
                    PrintStyle.display(PrintStyle.indent("Please input the correct format:"
                            + "event {description} /from {start time} /to {end time}"));
                }

            } else if (comms[0].equals("delete")) {
                try {
                    int ind = Integer.parseInt(comms[1]);
                    delete(ind);
                } catch (OutOfListException e) {
                    PrintStyle.display(PrintStyle.indent(e.getMessage()));
                } catch (NumberFormatException e) {
                    PrintStyle.display(PrintStyle.indent("please enter index after command 'delete'"));
                }
            } else {
                PrintStyle.display(PrintStyle.indent("Sorry, I have not learnt this command yet :("));
            }
            input = scanner.nextLine();
            comms = input.split(" ");
        }
        quit();
    }

    public static void main(String[] args) {
        MuyunBot bot = new MuyunBot();
        bot.run();
    }

}

