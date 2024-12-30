package io.github.bootystar.autoconfigure.spring.converter.support;

import io.github.bootystar.autoconfigure.spring.converter.DateTimeConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author bootystar
 */
public class String2LocalDateConverter implements DateTimeConverter<String, LocalDate> {
    private final DateTimeFormatter formatter;

    public String2LocalDateConverter(String pattern) {
        this.formatter = DateTimeFormatter.ofPattern(pattern);
    }

    @Override
    public LocalDate convert(String source) {
        if (source.isEmpty()) {
            return null;
        }
        return LocalDate.parse(source, formatter);
    }
}
