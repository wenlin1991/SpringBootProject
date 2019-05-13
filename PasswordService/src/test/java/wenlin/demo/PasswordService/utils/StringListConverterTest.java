package wenlin.demo.PasswordService.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringListConverterTest {

    private StringListConverter stringListConverter;

    @BeforeEach
    public void setUp() {
        stringListConverter = new StringListConverter();
    }

    @Test
    void convertToDatabaseColumn() {
        List<String> input = Arrays.asList(
                new String[]{"I", "want", "to", "join", "brain"});
        assertEquals("I,want,to,join,brain",
                stringListConverter.convertToDatabaseColumn(input));
    }

    @Test
    void convertToEntityAttribute() {
        String input = "I,want,to,join,brain";
        List<String> output = Arrays.asList(
                new String[]{"I", "want", "to", "join", "brain"});
        assertEquals(output,
                stringListConverter.convertToEntityAttribute(input));
    }
}