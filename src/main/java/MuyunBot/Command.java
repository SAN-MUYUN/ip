package MuyunBot;

import MuyunBot.classes.Parser;
import MuyunBot.exceptions.NoContentException;
import MuyunBot.exceptions.OutOfListException;

import java.time.format.DateTimeParseException;

public class Command {

    /**
     * Executes commands according to the comms passed in
     * @param comms parsed commands
     * @param taskList taskList to be updated when executing commands
     * @param parser Parser that is used to understand the commands and create tasks accordingly.
     */
    protected void execute(String[] comms, TaskList taskList, Parser parser) {
        switch (comms[0]) {
        case "list":
            taskList.showList();
            break;

        case "mark":
            try {
                int ind = Integer.parseInt(comms[1]);
                taskList.markAsDone(ind);
                break;
            } catch (OutOfListException e) {
                Ui.display(Ui.indent(e.getMessage()));
            } catch (NumberFormatException e) {
                Ui.display(Ui.indent("please enter index after the command 'mark'"));
            }

        case "unmark":
            try {
                int ind = Integer.parseInt(comms[1]);
                taskList.markAsUndone(ind);
                break;
            } catch (OutOfListException e) {
                Ui.display(Ui.indent(e.getMessage()));
            } catch (NumberFormatException e) {
                Ui.display(Ui.indent("please enter index after the command 'unmark'"));
            }

        case "todo":
            try {
                taskList.addTask(parser.createTodo(comms));
                break;
            } catch (NoContentException e) {
                Ui.display(Ui.indent(e.getMessage()));
            }

        case "deadline":
            try {
                taskList.addTask(parser.createDeadline(comms));
                break;
            } catch (NoContentException e) {
                Ui.display(Ui.indent(e.getMessage()));
            } catch (DateTimeParseException e) {
                Ui.display(Ui.indent("Please input deadline following format yyyy-mm-dd"));
            }

        case "event":
            try {
                taskList.addTask(parser.createEvent(comms));
                break;
            } catch (NoContentException e) {
                Ui.display(Ui.indent(e.getMessage()));
            } catch (DateTimeParseException e) {
                Ui.display(Ui.indent("Please input deadline following format yyyy-mm-dd"));
            } catch (ArrayIndexOutOfBoundsException e) {
                Ui.display(Ui.indent("Please input the correct format:"
                        + "event {description} /from {start time} /to {end time}"));
            }

        case "delete":
            try {
                int ind = Integer.parseInt(comms[1]);
                taskList.delete(ind);
                break;
            } catch (OutOfListException e) {
                Ui.display(Ui.indent(e.getMessage()));
            } catch (NumberFormatException e) {
                Ui.display(Ui.indent("please enter index after command 'delete'"));
            }

        default:
            Ui.display(Ui.indent("Sorry, I have not learnt this command yet :("));
        }
    }
}

