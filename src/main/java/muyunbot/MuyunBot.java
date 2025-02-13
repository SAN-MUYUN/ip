package muyunbot;

import javafx.scene.layout.VBox;
import muyunbot.exceptions.NoContentException;

import java.util.Scanner;

/**
 * Represents the model of the chat bot.
 */
public class MuyunBot {

    private TaskList taskList;
    private final Storage storage;

    private final Parser parser;
    private Ui ui;

    /**
     * Constructs a bot.
     */
    public MuyunBot() {
        this.ui = new Ui();
        this.storage = new Storage(this.ui);
        this.parser = new Parser();
        this.taskList = this.storage.sync(this.storage);
    }


    /**
     * Prints greeting message on commandline
     */
    private String greet() {
        String text = this.ui.indent("Hello! I'm MuyunBot")
                + this.ui.indent("What can I do for you?");
        return this.ui.display(text);
    }

    /**
     * Displays end message on the commandline
     */
    private String quit() {
        return this.ui.display(this.ui.indent("Bye! Hope to see you soon!"));
    }

    /**
     * Runs the whole program until user inputs "bye" in the commandline.
     */
    private void run() {
        Scanner scanner = new Scanner(System.in);
        Command c = new Command(this.ui);
        greet();
        String input = scanner.nextLine();
        while (!input.equals("bye")) {
            try {
                String[] comms = parser.generateCommand(input);
                c.execute(comms, this.taskList, this.parser);
            } catch (NoContentException e) {
                ui.display(e.getMessage());
            }

            input = scanner.nextLine();
        }
        quit();
    }

    public String getResponse(String input) {
        Command c = new Command(new Ui());
        try {
            String[] comms = parser.generateCommand(input);
            return c.execute(comms, this.taskList, this.parser);
        } catch (NoContentException e) {
            return e.getMessage();
        }
    }

    public static void main(String[] args) {
        MuyunBot bot = new MuyunBot();
        bot.run();
    }

}

