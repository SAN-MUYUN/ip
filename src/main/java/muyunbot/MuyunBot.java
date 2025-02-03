package muyunbot;

import java.util.Scanner;

/**
 * Represents the model of the chat bot.
 */
public class MuyunBot {

    private TaskList taskList;
    private final Storage storage;

    private final Parser parser;

    /**
     * Constructs a bot.
     */
    public MuyunBot() {
        this.storage = new Storage();
        this.parser = new Parser();
        this.taskList = this.storage.sync(this.storage);
    }


    /**
     * Prints greeting message on commandline
     */
    private void greet() {
        String text = Ui.indent("Hello! I'm MuyunBot")
                + Ui.indent("What can I do for you?");
        Ui.display(text);
    }

    /**
     * Displays end message on the commandline
     */
    private void quit() {
        Ui.display(Ui.indent("Bye! Hope to see you soon!"));
    }

    /**
     * Runs the whole program until user inputs "bye" in the commandline.
     */
    private void run() {
        Scanner scanner = new Scanner(System.in);
        Command c = new Command();
        greet();
        String input = scanner.nextLine();
        while (!input.equals("bye")) {
            String[] comms = parser.generateCommand(input);
            c.execute(comms, this.taskList, this.parser);
            input = scanner.nextLine();
        }
        quit();
    }

    public static void main(String[] args) {
        MuyunBot bot = new MuyunBot();
        bot.run();

    }

}

