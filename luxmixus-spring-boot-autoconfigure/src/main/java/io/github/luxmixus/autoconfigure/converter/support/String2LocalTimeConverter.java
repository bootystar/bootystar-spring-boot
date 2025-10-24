package io.github.luxmixus.autoconfigure.converter.support;

import io.github.luxmixus.autoconfigure.converter.DateTimeConverter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author luxmixus
 */
public class String2LocalTimeConverter implements DateTimeConverter<String, LocalTime> {
    private final DateTimeFormatter formatter;

    public String2LocalTimeConverter(String pattern) {
        this.formatter = DateTimeFormatter.ofPattern(pattern);
    }

    @Override
    public LocalTime convert(String source) {
        if (source.isEmpty()) {
            return null;
        }
        return LocalTime.parse(source, formatter);
    }

}
