package muyunbot;

import muyunbot.exceptions.OutOfListException;
import muyunbot.tasks.Task;
import muyunbot.tasks.Todo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TaskListTest {
    @Test
    public void addTask_todo_success() {
        ArrayList<Task> testArray = new ArrayList<>();
        TaskList sut = new TaskList(new Storage(new Ui()), testArray, new Ui());
        Todo testTodo = new Todo("testing");
        sut.addTask(testTodo);

        // gets the task from the testArray, and compare it with the initially created Todo, they should have the
        // same toString() output.
        Task taskToTest = testArray.get(0);

        assertEquals(testTodo.toString(), taskToTest.toString());

    }

    @Test
    public void markAsDone_success() {
        //Sets up the testing TaskList for testing.
        ArrayList<Task> testArray = new ArrayList<>();
        TaskList sut = new TaskList(new Storage(new Ui()), testArray, new Ui());
        Todo testTodo = new Todo("testing");
        sut.addTask(testTodo);

        try {
            sut.markAsDone(1);
        } catch (OutOfListException e) {
            //code should not reach this line.
            Assertions.fail();
        }
        assertEquals(testArray.get(0).getStatusIcon(), "X");
    }

    @Test
    public void markAsDone_indexTooLarge_exceptionThrown() {
        //Sets up the testing TaskList for testing.
        ArrayList<Task> testArray = new ArrayList<>();
        TaskList sut = new TaskList(new Storage(new Ui()), testArray, new Ui());
        Todo testTodo = new Todo("testing");
        sut.addTask(testTodo);

        try {
            sut.markAsDone(2);
            fail();
        } catch (OutOfListException e) {
            assertEquals("index 2"
                    + " is out of the list, please double check your index~", e.getMessage());
        }

    }

    @Test
    public void markAsDone_indexTooSmall_exceptionThrown() {
        //Sets up the testing TaskList for testing.
        ArrayList<Task> testArray = new ArrayList<>();
        TaskList sut = new TaskList(new Storage(new Ui()), testArray, new Ui());
        Todo testTodo = new Todo("testing");
        sut.addTask(testTodo);

        try {
            sut.markAsDone(0);
            fail();
        } catch (OutOfListException e) {
            assertEquals("Task number should be greater than 0", e.getMessage());
        }

    }

    @Test
    public void markAsDone_negativeIndex_exceptionThrown() {
        //Sets up the testing TaskList for testing.
        ArrayList<Task> testArray = new ArrayList<>();
        TaskList sut = new TaskList(new Storage(new Ui()), testArray, new Ui());
        Todo testTodo = new Todo("testing");
        sut.addTask(testTodo);

        try {
            sut.markAsDone(-2);
            fail();
        } catch (OutOfListException e) {
            assertEquals("Task number should be greater than 0", e.getMessage());
        }

    }
}
