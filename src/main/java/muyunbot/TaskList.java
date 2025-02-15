package muyunbot;

import java.io.IOException;
import java.util.ArrayList;

import muyunbot.exceptions.NoTaskPropertyException;
import muyunbot.exceptions.OutOfListException;
import muyunbot.tasks.Task;


/**
 * Represents the task list with methods to handle the tasks in the task list.
 */
public class TaskList {

    /** ArrayList containing all the tasks */
    private final ArrayList<Task> TASK_LIST;
    private Storage storage;
    private Ui ui;

    /**
     * Constructs a TaskList object
     * @param storage Storage object used to write to files.
     */
    public TaskList(Storage storage, Ui ui) {
        this.TASK_LIST = new ArrayList<>();
        this.storage = storage;
        this.ui = ui;
    }

    /**
     * Constructs a TaskList from a given list of tasks.
     * @param storage Storage object used to write and retrieve data from file.
     * @param list List containing tasks used to populates the TaskList.
     */
    public TaskList(Storage storage, ArrayList<Task> list, Ui ui) {
        this.storage = storage;
        this.TASK_LIST = list;
        this.ui = ui;
    }

    /**
     * Adds a new task to the tasklist.
     * Updates the storage file accordingly.
     * @param newTask New Task to be added to the task list
     */
    protected String addTask(Task newTask) {
        assert this.TASK_LIST != null : "TaskList not initialzed";
        TASK_LIST.add(newTask);
        String text = this.ui.indent("new task is here!")
                + this.ui.indent("added: " + newTask.toString())
                + this.ui.indent("now we have " + TASK_LIST.size() + " tasks in the list");
        this.storage.writeFile(newTask.toObjStr());
        return this.ui.display(text);
    }

    /**
     * Marks a task with index ind in TASK_LIST as done. Displays a message after marking as done.
     * @param ind index to be marked as done in the TASK_LIST.
     * @exception OutOfListException when index is larger than number of Tasks.
     */
    protected String markAsDone(int ind) throws OutOfListException {
        checkInd(ind);
        TASK_LIST.get(ind - 1).markAsDone();
        String text = this.ui.indent("well done, 1 task down!\n");
        text += this.ui.indent(TASK_LIST.get(ind - 1).toString());
        try {
            this.storage.updateFile(TASK_LIST);
        } catch (IOException e) {
            return "Error updating file";
        }
        return this.ui.display(text);


    }

    /**
     * Marks a task in TASK_LIST as undone. Displays a message after that.
     * @param ind index to be marked as undone in the TASK_LIST.
     * @exception OutOfListException when index is larger than number of Tasks.
     */
    protected String markAsUndone(int ind) throws OutOfListException {
        checkInd(ind);
        TASK_LIST.get(ind - 1).markNotDone();
        String text = this.ui.indent("oops, seems like this task isn't done yet...\n");
        text += this.ui.indent(TASK_LIST.get(ind - 1).toString());
        try {
            this.storage.updateFile(TASK_LIST);
        } catch (IOException e) {
            return "Error writing to file";
        }
        return this.ui.display(text);
    }

    /**
     * Displays the content in the TASK_LIST.
     */
    protected String showList() {
        StringBuilder listContent = new StringBuilder();
        for (int i = 0; i < TASK_LIST.size(); i++) {
            listContent.append(this.ui.indent((i + 1) + ". " + TASK_LIST.get(i).toString()));
        }
        return this.ui.display(listContent.toString());

    }

    /**
     * Deletes a task from the TASK_LIST.
     * @param ind task index to be deleted
     * @throws OutOfListException if ind exceeds the size of the ArrayList TASK_LIST.
     */
    protected String delete(int ind) throws OutOfListException {
        checkInd(ind);
        Task toBeRemoved = TASK_LIST.get(ind - 1);
        TASK_LIST.remove(ind - 1);
        String text = this.ui.indent("I am removing this task:")
                + this.ui.indent(toBeRemoved.toString())
                + this.ui.indent("Now there are " + TASK_LIST.size() + " tasks in the list");
        try {
            this.storage.updateFile(TASK_LIST);
        } catch (IOException e) {
            return "error updating file.";
        }

        return this.ui.display(text);
    }

    protected String find(String text) {
        StringBuilder listContent = new StringBuilder();
        for (int i = 0; i < TASK_LIST.size(); i++) {
            if (TASK_LIST.get(i).describe().contains(text)) {
                listContent.append(this.ui.indent((i + 1) + ". " + TASK_LIST.get(i).toString()));
            }

        }
        return this.ui.display(listContent.toString());
    }

    /**
     * Updates the content of a particular task.
     * @param ind Index of task to be updated
     * @param updateInfo Information about what to update and content of the new input.
     * @return String output from the update.
     */
    protected String update(int ind, ArrayList<String[]> updateInfo) throws OutOfListException,
            NoTaskPropertyException {
        checkInd(ind);
        Task toBeUpdated = TASK_LIST.get(ind - 1);
        for (String[] info : updateInfo) {
            toBeUpdated.update(info);
        }
        try {
            storage.updateFile(TASK_LIST);
        } catch (IOException e) {
            return "Error updating file.";
        }
        String output = ui.indent("updated: ")
                + ui.indent(toBeUpdated.toString());
        return this.ui.display(output);
    }

    protected void checkInd(int ind) throws OutOfListException {
        if (ind > TASK_LIST.size()) {
            throw new OutOfListException("index "
                    + ind
                    + " is out of the list, please double check your index~");
        } else if (ind <= 0) {
            throw new OutOfListException("Task number should be greater than 0");
        }
    }
}
