package muyunBot;

import muyunBot.classes.Task;
import muyunBot.exceptions.OutOfListException;

import java.util.ArrayList;

public class TaskList {

    /** ArrayList containing all the tasks */
    private final ArrayList<Task> TASKLIST;

    private Storage storage;

    public TaskList(Storage storage) {
        this.TASKLIST = new ArrayList<>();
        this.storage = storage;
    }

    public TaskList(Storage storage, ArrayList<Task> list) {
        this.storage = storage;
        this.TASKLIST = list;
    }

    /**
     * Adds a new task to the tasklist.
     * Updates the storage file accordingly.
     * @param newTask New Task to be added to the task list
     */
    protected void addTask(Task newTask) {
        TASKLIST.add(newTask);
        String text = Ui.indent("new task is here!")
                + Ui.indent("added: " + newTask.toString())
                + Ui.indent("now we have " + TASKLIST.size() + " tasks in the list");
        Ui.display(text);
        this.storage.writeFile(newTask.toObjStr());
    }

    /**
     * Marks a task with index ind in TASKLIST as done. Displays a message after marking as done.
     * @param ind index to be marked as done in the TASKLIST.
     * @exception OutOfListException when index is larger than number of Tasks.
     */
    protected void markAsDone(int ind) throws OutOfListException {
        if (ind > TASKLIST.size()) {
            throw new OutOfListException("Task number "
                    + ind
                    + " is out of the list of size "+ TASKLIST.size() +", please double check your index~");
        }
        TASKLIST.get(ind - 1).markAsDone();
        String text = Ui.indent("well done, 1 task down!\n");
        text += Ui.indent(TASKLIST.get(ind - 1).toString());
        Ui.display(text);
        this.storage.updateFile(TASKLIST);
    }

    /**
     * Marks a task in TASKLIST as undone. Displays a message after that.
     * @param ind index to be marked as undone in the TASKLIST.
     * @exception OutOfListException when index is larger than number of Tasks.
     */
    protected void markAsUndone(int ind) throws OutOfListException {
        if (ind > TASKLIST.size()) {
            throw new OutOfListException("index "
                    + ind
                    + " is out of the list, please double check your index~");
        }
        TASKLIST.get(ind - 1).markNotDone();
        String text = Ui.indent("oops, seems like this task isn't done yet...\n");
        text += Ui.indent(TASKLIST.get(ind - 1).toString());
        Ui.display(text);
        this.storage.updateFile(TASKLIST);
    }

    /**
     * Displays the content in the TASKLIST.
     */
    protected void showList() {
        StringBuilder listContent = new StringBuilder();
        for (int i = 0; i < TASKLIST.size(); i++) {
            listContent.append(Ui.indent(
                    (i + 1) + ". " + TASKLIST.get(i).toString()));
        }
        Ui.display(listContent.toString());
    }

    /**
     * Deletes a task from the TASKLIST.
     * @param ind task index to be deleted
     * @throws OutOfListException if ind exceeds the size of the ArrayList TASKLIST.
     */
    protected void delete(int ind) throws OutOfListException{
        if (ind > TASKLIST.size()) {
            throw new OutOfListException("index " + ind + " is out of the list,"
                    + " please double check your index~");
        }
        Task toBeRemoved = TASKLIST.get(ind - 1);
        TASKLIST.remove(ind - 1);
        String text = Ui.indent("I am removing this task:")
                + Ui.indent(toBeRemoved.toString())
                + Ui.indent("Now there are " + TASKLIST.size() + " tasks in the list");
        Ui.display(text);
        this.storage.updateFile(TASKLIST);
    }

    protected void find(String text) {
        StringBuilder listContent = new StringBuilder();
        for (int i = 0; i < TASKLIST.size(); i++) {
            if (TASKLIST.get(i).describe().contains(text)) {
                listContent.append(Ui.indent(
                        (i + 1) + ". " + TASKLIST.get(i).toString()));
            }

        }
        Ui.display(listContent.toString());
    }
}
