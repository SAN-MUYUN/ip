package muyunBot;

import muyunBot.classes.Todo;
import muyunBot.exceptions.NoContentException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ParserTest {
    @Test
    public void parseDate_validDate_success() {
        String testInput = "2025-01-30";
        LocalDate expected = LocalDate.parse(testInput);
        assertEquals(expected, Parser.parseDate(testInput));
    }

    @Test
    public void parseDate_invalidDateFormat_exceptionThrown() {
        String testInput = "2025-101-30";
        try {
            Parser.parseDate(testInput);
            fail();
        } catch (DateTimeParseException e) {
            assertEquals("Text '2025-101-30' could not be parsed at index 7", e.getMessage());
        }
    }

    @Test
    public void parseDate_invalidMonth_exceptionThrown() {
        String testInput = "2025-13-30";
        try {
            Parser.parseDate(testInput);
            fail();
        } catch (DateTimeParseException e) {
            assertEquals("Text '2025-13-30' could not be parsed", e.getMessage().substring(0, 37));
        }
    }

    @Test
    public void createTodo_validInput_success() {
        Parser sut = new Parser();
        Todo expected = new Todo("testing");
        String[] testInput = {"todo", "testing"};

        // creates a new Todo object using testInput, then test to see if the content of the 2 Todo Objects
        // have the same String representation and the same ObjStr representation.
        try {
            Todo output;
            output = sut.createTodo(testInput);
            assertEquals(expected.toString(), output.toString());
            assertEquals(expected.toObjStr(), output.toObjStr());
        } catch (NoContentException e) {
            fail();
        }
    }
}
