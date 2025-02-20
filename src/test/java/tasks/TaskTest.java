package tasks;

import muyunbot.tasks.Task;
import muyunbot.tasks.Todo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTest {
    @Test
    public void markAsDone_success() {
        Task sut = new Todo("1");
        sut.markAsDone();
        assertEquals("X", sut.getStatusIcon());
    }

    @Test
    public void markAsUndone_success() {
        Task sut = new Todo("1");
        sut.markAsDone();
        assertEquals("X", sut.getStatusIcon());
        sut.markNotDone();
        assertEquals(" ", sut.getStatusIcon());
    }
}
