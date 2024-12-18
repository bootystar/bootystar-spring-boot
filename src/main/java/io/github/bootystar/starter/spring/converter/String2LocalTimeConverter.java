package io.github.bootystar.starter.spring.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author bootystar
 * @since 2023/10/27
 */
public class String2LocalTimeConverter implements Converter<String, LocalTime> {

    private final DateTimeFormatter formatter;
    
    public String2LocalTimeConverter(String format) {
        this.formatter = DateTimeFormatter.ofPattern(format);
    }
    
    @Override
    public LocalTime convert(String source) {
        if (source.isEmpty()) {
            return null;
        }
        int length = source.length();
        switch (length) {
            case 2:
                source += ":00:00";
                break;
            case 5:
                source += ":00";
            default:
                break;
        }
        return LocalTime.parse(source, formatter);
    }

}
