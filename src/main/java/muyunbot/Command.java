package muyunbot;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import muyunbot.exceptions.NoContentException;
import muyunbot.exceptions.NoTaskPropertyException;
import muyunbot.exceptions.OutOfListException;
import muyunbot.exceptions.TimeTravelException;

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
        assert comm != null && comm.length > 0 : "Command array cannot be null or empty";

        switch (comm[0].toLowerCase()) {
        case "list":
            return taskList.showList();
        case "mark":
            try {
                int taskInd = Integer.parseInt(comm[1]);
                return taskList.markAsDone(taskInd);
            } catch (OutOfListException e) {
                return this.ui.display(this.ui.indent(e.getMessage()));
            } catch (NumberFormatException e) {
                return this.ui.display(this.ui.indent("please enter index after the command 'mark'"));
            }

        case "unmark":
            try {
                int taskInd = Integer.parseInt(comm[1]);
                return taskList.markAsUndone(taskInd);
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
            } catch (NoContentException | TimeTravelException e) {
                return this.ui.display(this.ui.indent(e.getMessage()));
            } catch (DateTimeParseException e) {
                return this.ui.display(this.ui.indent("Please input the time following format yyyy-mm-dd"));
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
            //finds tasks with component that contains the keyword.
            StringBuilder searchText = new StringBuilder();
            for (int i = 1; i < comm.length; i++) {
                if (!searchText.isEmpty()) {
                    searchText.append(" ");
                }
                searchText.append(comm[i]);
            }
            return taskList.find(searchText.toString());

        case "update":
            int taskInd = Integer.parseInt(comm[1]);
            try {
                ArrayList<String[]> toBeUpdated = parser.parseUpdate(comm);
                return taskList.update(taskInd, toBeUpdated);
            } catch (OutOfListException | NoTaskPropertyException | NoContentException e) {
                return this.ui.display(this.ui.indent(e.getMessage()));
            }
        case "bye":
            return this.ui.display(this.ui.indent("Goodbye! Hope to see you soon!"));
        default:
            return this.ui.display(this.ui.indent("Sorry, I have not learnt this command yet :("));
        }
    }
}

