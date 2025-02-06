package muyunbot;

import java.time.format.DateTimeParseException;

import muyunbot.exceptions.NoContentException;
import muyunbot.exceptions.OutOfListException;

/**
 * Handles the commands generated from user input.
 */
public class Command {
    private Ui ui;

    public Command(Ui ui) {
        this.ui = ui;
    }

    /**
     * Executes commands according to the comms passed in
     * @param comm parsed commands
     * @param taskList taskList to be updated when executing commands
     * @param parser Parser that is used to understand the commands and create tasks accordingly.
     */
    protected String execute(String[] comm, TaskList taskList, Parser parser) {
        switch (comm[0]) {
        case "list":
            return taskList.showList();
        case "mark":
            try {
                int ind = Integer.parseInt(comm[1]);
                return taskList.markAsDone(ind);
            } catch (OutOfListException e) {
                return this.ui.display(this.ui.indent(e.getMessage()));
            } catch (NumberFormatException e) {
                return this.ui.display(this.ui.indent("please enter index after the command 'mark'"));
            }

        case "unmark":
            try {
                int ind = Integer.parseInt(comm[1]);
                return taskList.markAsUndone(ind);
            } catch (OutOfListException e) {
                return this.ui.display(this.ui.indent(e.getMessage()));
            } catch (NumberFormatException e) {
                return this.ui.display(this.ui.indent("please enter index after the command 'unmark'"));
            }

        case "todo":
            try {
                return taskList.addTask(parser.createTodo(comm));
            } catch (NoContentException e) {
                return this.ui.display(this.ui.indent(e.getMessage()));
            }

        case "deadline":
            try {
                return taskList.addTask(parser.createDeadline(comm));
            } catch (NoContentException e) {
                return this.ui.display(this.ui.indent(e.getMessage()));
            } catch (DateTimeParseException e) {
                return this.ui.display(this.ui.indent("Please input deadline following format yyyy-mm-dd"));
            }

        case "event":
            try {
                return taskList.addTask(parser.createEvent(comm));
            } catch (NoContentException e) {
                return this.ui.display(this.ui.indent(e.getMessage()));
            } catch (DateTimeParseException e) {
                return this.ui.display(this.ui.indent("Please input deadline following format yyyy-mm-dd"));
            } catch (ArrayIndexOutOfBoundsException e) {
                return this.ui.display(this.ui.indent("Please input the correct format:"
                        + "event {description} /from {start time} /to {end time}"));
            }

        case "delete":
            try {
                int ind = Integer.parseInt(comm[1]);
                return taskList.delete(ind);
            } catch (OutOfListException e) {
                return this.ui.display(this.ui.indent(e.getMessage()));
            } catch (NumberFormatException e) {
                return this.ui.display(this.ui.indent("please enter index after command 'delete'"));
            }

        case "find":
            // find the corresponding element
            StringBuilder searchText = new StringBuilder();
            for (int i = 1; i < comm.length; i++) {
                if (!searchText.isEmpty()) {
                    searchText.append(" ");
                }
                searchText.append(comm[i]);
            }
            return taskList.find(searchText.toString());
        case "bye":
            return this.ui.display(this.ui.indent("Goodbye! Hope to see you soon!"));
        default:
            return this.ui.display(this.ui.indent("Sorry, I have not learnt this command yet :("));
        }
    }
}

