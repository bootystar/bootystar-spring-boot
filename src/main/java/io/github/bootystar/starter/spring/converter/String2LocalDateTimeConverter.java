package io.github.bootystar.starter.spring.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author bootystar
 * @since 2023/10/27
 */
public class String2LocalDateTimeConverter implements Converter<String, LocalDateTime> {
    
    private final DateTimeFormatter formatter;
    
    public String2LocalDateTimeConverter(String format) {
        this.formatter = DateTimeFormatter.ofPattern(format);
    }

    @Override
    public LocalDateTime convert(String source) {
        if (source.isEmpty()) {
            return null;
        }
        int length = source.length();
        source = source.replace('T', ' ');
        switch (length) {
            case 4:
                source += "-01-01 00:00:00";
                break;
            case 7:
                source += "-01 00:00:00";
                break;
            case 10:
                source += " 00:00:00";
                break;
            case 13:
                source += ":00:00";
                break;
            case 16:
                source += ":00";
                break;
            default:
                break;
        }
        return LocalDateTime.parse(source, formatter);
    }
}
