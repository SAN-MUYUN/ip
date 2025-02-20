package tasks;

import muyunbot.exceptions.NoTaskPropertyException;
import muyunbot.tasks.Deadline;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class DeadlineTest {
    @Test
    public void update_updateDescription_success() {
        Deadline sut = new Deadline("buy books", "2025-02-23", false);
        assertEquals("buy books", sut.describe());
        try {
            sut.update(new String[]{"description", "return books"});
            assertEquals("return books", sut.describe());
        } catch (NoTaskPropertyException e) {
            fail();
        }
    }

    @Test
    public void update_updateDeadline_success() {
        Deadline sut = new Deadline("buy books", "2025-02-23", false);
        assertEquals("[D][ ] buy books (by: SUN Feb 23 2025)", sut.toString());
        try {
            sut.update(new String[]{"deadline", "2026-03-23"});
            assertEquals("[D][ ] buy books (by: MON Mar 23 2026)", sut.toString());
        } catch (NoTaskPropertyException e) {
            fail();
        }
    }

    @Test
    public void update_invalidProperty_exceptionThrown() {
        Deadline sut = new Deadline("buy books", "2025-02-23", false);
        assertEquals("[D][ ] buy books (by: SUN Feb 23 2025)", sut.toString());
        try {
            sut.update(new String[]{"endTime", "2026-03-23"});
            fail();
        } catch (NoTaskPropertyException e) {
            assertEquals("No Such Attribute: endTime in Deadline", e.getMessage());
        }
    }
}
