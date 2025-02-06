package muyunbot;

import java.util.ArrayList;

import muyunbot.classes.Task;
import muyunbot.exceptions.OutOfListException;

/**
 * Represents the task list with methods to handle the tasks in the task list.
 */
public class TaskList {

    /** ArrayList containing all the tasks */
    private final ArrayList<Task> TASKLIST;
    private Storage storage;
    private Ui ui;

    /**
     * Constructs a TaskList object
     * @param storage Storage object used to write to files.
     */
    public TaskList(Storage storage, Ui ui) {
        this.TASKLIST = new ArrayList<>();
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
        this.TASKLIST = list;
        this.ui = ui;
    }

    /**
     * Adds a new task to the tasklist.
     * Updates the storage file accordingly.
     * @param newTask New Task to be added to the task list
     */
    protected String addTask(Task newTask) {
        TASKLIST.add(newTask);
        String text = this.ui.indent("new task is here!")
                + this.ui.indent("added: " + newTask.toString())
                + this.ui.indent("now we have " + TASKLIST.size() + " tasks in the list");
        this.storage.writeFile(newTask.toObjStr());
        return this.ui.display(text);
    }

    /**
     * Marks a task with index ind in TASKLIST as done. Displays a message after marking as done.
     * @param ind index to be marked as done in the TASKLIST.
     * @exception OutOfListException when index is larger than number of Tasks.
     */
    protected String markAsDone(int ind) throws OutOfListException {
        if (ind > TASKLIST.size()) {
            throw new OutOfListException("Task number "
                    + ind
                    + " is out of the list of size " + TASKLIST.size() + ", please double check your index~");
        }
        TASKLIST.get(ind - 1).markAsDone();
        String text = this.ui.indent("well done, 1 task down!\n");
        text += this.ui.indent(TASKLIST.get(ind - 1).toString());
        this.storage.updateFile(TASKLIST);
        return this.ui.display(text);


    }

    /**
     * Marks a task in TASKLIST as undone. Displays a message after that.
     * @param ind index to be marked as undone in the TASKLIST.
     * @exception OutOfListException when index is larger than number of Tasks.
     */
    protected String markAsUndone(int ind) throws OutOfListException {
        if (ind > TASKLIST.size()) {
            throw new OutOfListException("index "
                    + ind
                    + " is out of the list, please double check your index~");
        }
        TASKLIST.get(ind - 1).markNotDone();
        String text = this.ui.indent("oops, seems like this task isn't done yet...\n");
        text += this.ui.indent(TASKLIST.get(ind - 1).toString());
        this.storage.updateFile(TASKLIST);
        return this.ui.display(text);
    }

    /**
     * Displays the content in the TASKLIST.
     */
    protected String showList() {
        StringBuilder listContent = new StringBuilder();
        for (int i = 0; i < TASKLIST.size(); i++) {
            listContent.append(this.ui.indent((i + 1) + ". " + TASKLIST.get(i).toString()));
        }
        return this.ui.display(listContent.toString());

    }

    /**
     * Deletes a task from the TASKLIST.
     * @param ind task index to be deleted
     * @throws OutOfListException if ind exceeds the size of the ArrayList TASKLIST.
     */
    protected String delete(int ind) throws OutOfListException {
        if (ind > TASKLIST.size()) {
            throw new OutOfListException("index " + ind + " is out of the list,"
                    + " please double check your index~");
        }
        Task toBeRemoved = TASKLIST.get(ind - 1);
        TASKLIST.remove(ind - 1);
        String text = this.ui.indent("I am removing this task:")
                + this.ui.indent(toBeRemoved.toString())
                + this.ui.indent("Now there are " + TASKLIST.size() + " tasks in the list");
        this.storage.updateFile(TASKLIST);
        return this.ui.display(text);
    }

    protected String find(String text) {
        StringBuilder listContent = new StringBuilder();
        for (int i = 0; i < TASKLIST.size(); i++) {
            if (TASKLIST.get(i).describe().contains(text)) {
                listContent.append(this.ui.indent((i + 1) + ". " + TASKLIST.get(i).toString()));
            }

        }
        return this.ui.display(listContent.toString());
    }
}
