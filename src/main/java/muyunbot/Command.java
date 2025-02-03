package muyunbot;

import java.time.format.DateTimeParseException;

import muyunbot.exceptions.NoContentException;
import muyunbot.exceptions.OutOfListException;

/**
 * Handles the commands generated from user input.
 */
public class Command {

    /**
     * Executes commands according to the comms passed in
     * @param comm parsed commands
     * @param taskList taskList to be updated when executing commands
     * @param parser Parser that is used to understand the commands and create tasks accordingly.
     */
    protected void execute(String[] comm, TaskList taskList, Parser parser) {
        switch (comm[0]) {
        case "list":
            taskList.showList();
            break;

        case "mark":
            try {
                int ind = Integer.parseInt(comm[1]);
                taskList.markAsDone(ind);
            } catch (OutOfListException e) {
                Ui.display(Ui.indent(e.getMessage()));
            } catch (NumberFormatException e) {
                Ui.display(Ui.indent("please enter index after the command 'mark'"));
            }
            break;

        case "unmark":
            try {
                int ind = Integer.parseInt(comm[1]);
                taskList.markAsUndone(ind);
            } catch (OutOfListException e) {
                Ui.display(Ui.indent(e.getMessage()));
            } catch (NumberFormatException e) {
                Ui.display(Ui.indent("please enter index after the command 'unmark'"));
            }
            break;

        case "todo":
            try {
                taskList.addTask(parser.createTodo(comm));
            } catch (NoContentException e) {
                Ui.display(Ui.indent(e.getMessage()));
            }
            break;

        case "deadline":
            try {
                taskList.addTask(parser.createDeadline(comm));
            } catch (NoContentException e) {
                Ui.display(Ui.indent(e.getMessage()));
            } catch (DateTimeParseException e) {
                Ui.display(Ui.indent("Please input deadline following format yyyy-mm-dd"));
            }
            break;

        case "event":
            try {
                taskList.addTask(parser.createEvent(comm));
            } catch (NoContentException e) {
                Ui.display(Ui.indent(e.getMessage()));
            } catch (DateTimeParseException e) {
                Ui.display(Ui.indent("Please input deadline following format yyyy-mm-dd"));
            } catch (ArrayIndexOutOfBoundsException e) {
                Ui.display(Ui.indent("Please input the correct format:"
                        + "event {description} /from {start time} /to {end time}"));
            }
            break;

        case "delete":
            try {
                int ind = Integer.parseInt(comm[1]);
                taskList.delete(ind);
            } catch (OutOfListException e) {
                Ui.display(Ui.indent(e.getMessage()));
            } catch (NumberFormatException e) {
                Ui.display(Ui.indent("please enter index after command 'delete'"));
            }
            break;

        case "find":
            // find the corresponding element
            StringBuilder searchText = new StringBuilder();
            for (int i = 1; i < comm.length; i++) {
                if (!searchText.isEmpty()) {
                    searchText.append(" ");
                }
                searchText.append(comm[i]);
            }
            taskList.find(searchText.toString());
            break;

        default:
            Ui.display(Ui.indent("Sorry, I have not learnt this command yet :("));
        }
    }
}

