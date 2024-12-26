package io.github.bootystar.starter.spring.converter.support;

import io.github.bootystar.starter.spring.converter.DateTimeConverter;

import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author bootystar
 */
public class String2SqlTimeConverter implements DateTimeConverter<String, Time> {
    private final DateTimeFormatter formatter;

    public String2SqlTimeConverter(String pattern) {
        this.formatter = DateTimeFormatter.ofPattern(pattern);
    }

    @Override
    public Time convert(String source) {
        if (source.isEmpty()) {
            return null;
        }
        return Time.valueOf(LocalTime.parse(source, formatter));
    }

}
