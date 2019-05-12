package wenlin.demo.PasswordService.utils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * StringListConverter converts a list of string into a string
 * separated by coma. When use in entity class, entity table
 * will save string list as string. When fetch back from database,
 * delimited string will be converted to string list.
 *
 * @author wenlin
 */
@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

    @Override
    public String convertToDatabaseColumn(List<String> list) {
        return String.join(",", list);
    }

    @Override
    public List<String> convertToEntityAttribute(String joined) {
        return new ArrayList<>(Arrays.asList(joined.split(",")));
    }

}