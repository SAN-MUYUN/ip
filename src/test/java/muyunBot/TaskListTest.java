package muyunBot;

import muyunBot.classes.Task;
import muyunBot.classes.Todo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskListTest {
    @Test
    public void addTask_todo_success() {
        ArrayList<Task> testArray = new ArrayList<>();
        TaskList sut = new TaskList(new Storage(), testArray);
        Todo testTodo = new Todo("testing");
        sut.addTask(testTodo);

        // get the task from the testArray, and compare it with the initially created Todo, they should have the
        // same toString() output.
        Task taskToTest = testArray.get(0);

        assertEquals(testTodo.toString(), taskToTest.toString());

    }
}
