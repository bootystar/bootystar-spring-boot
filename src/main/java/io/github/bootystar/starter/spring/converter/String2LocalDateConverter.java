package io.github.bootystar.starter.spring.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author bootystar
 * @since 2023/10/27
 */
public class String2LocalDateConverter implements Converter<String, LocalDate> {

    private final DateTimeFormatter formatter;
    
    public String2LocalDateConverter(String format) {
        this.formatter = DateTimeFormatter.ofPattern(format);
    }

    @Override
    public LocalDate convert(String source) {
        if (source.isEmpty()) {
            return null;
        }
        int length = source.length();
        switch (length) {
            case 4:
                source += "-01-01";
                break;
            case 7:
                source += "-01";
                break;
            default:
                break;
        }
        return LocalDate.parse(source, formatter);
    }
}
