package muyunbot;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UiTest {
    @Test
    public void dashedlines_drawLine(){
        assertEquals("    __________\n", Ui.dashedLines());
    }

    @Test
    public void indent_normalText_success(){
        assertEquals("    hi\n", Ui.indent("hi"));
    }

    @Test
    public void displayDate_validDateInput_success() {
        LocalDate testDateInput = LocalDate.parse("2025-01-30");
        assertEquals("THU Jan 30 2025", Ui.displayDate(testDateInput));
    }
}
