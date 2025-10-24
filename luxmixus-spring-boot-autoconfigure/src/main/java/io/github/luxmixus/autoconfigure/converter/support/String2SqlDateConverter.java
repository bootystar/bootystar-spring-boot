package io.github.luxmixus.autoconfigure.converter.support;

import io.github.luxmixus.autoconfigure.converter.DateTimeConverter;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author luxmixus
 */
public class String2SqlDateConverter implements DateTimeConverter<String, Date> {
    private final DateTimeFormatter formatter;

    public String2SqlDateConverter(String pattern) {
        this.formatter = DateTimeFormatter.ofPattern(pattern);
    }

    @Override
    public Date convert(String source) {
        if (source.isEmpty()) {
            return null;
        }
        return Date.valueOf(LocalDate.parse(source, formatter));
    }

}
