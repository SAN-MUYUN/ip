package tasks;

import muyunbot.exceptions.NoTaskPropertyException;
import muyunbot.tasks.Todo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TodoTest {
    @Test
    public void update_validInput_success() {
        Todo sut = new Todo("buy books");
        assertEquals(sut.describe(), "buy books");
        try {
            sut.update(new String[]{"description", "return books"});
            assertEquals(sut.describe(), "return books");
        } catch (NoTaskPropertyException e) {
            fail();
        }

    }

    @Test
    public void update_wrongProperty_exceptionThrown() {
        Todo sut = new Todo("buy books");
        assertEquals(sut.describe(), "buy books");
        try {
            sut.update(new String[]{"startTime", "2025-02-23"});
            fail();
        } catch (NoTaskPropertyException e) {
            assertEquals("No Such Attribute: startTime in Todo", e.getMessage());
        }

    }
}
