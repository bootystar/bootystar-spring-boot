package io.github.luxmixus.autoconfigure.converter.support;

import io.github.luxmixus.autoconfigure.converter.DateTimeConverter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author luxmixus
 */
public class String2SqlTimestampConverter implements DateTimeConverter<String, Timestamp> {
    private final DateTimeFormatter formatter;

    public String2SqlTimestampConverter(String pattern) {
        this.formatter = DateTimeFormatter.ofPattern(pattern);
    }

    @Override
    public Timestamp convert(String source) {
        if (source.isEmpty()) {
            return null;
        }
        return Timestamp.valueOf(LocalDateTime.parse(source, formatter));
    }
}
