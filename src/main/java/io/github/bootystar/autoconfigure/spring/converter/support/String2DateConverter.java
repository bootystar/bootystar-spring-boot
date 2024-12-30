package io.github.bootystar.autoconfigure.spring.converter.support;

import io.github.bootystar.autoconfigure.spring.converter.DateTimeConverter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author bootystar
 */
public class String2DateConverter implements DateTimeConverter<String, Date> {
    private final DateTimeFormatter formatter;
    private final ZoneId zoneId;

    public String2DateConverter(String pattern, String zoneId) {
        this.formatter = DateTimeFormatter.ofPattern(pattern);
        this.zoneId = ZoneId.of(zoneId);
    }

    @Override
    public Date convert(String source) {
        if (source.isEmpty()) {
            return null;
        }
        return Date.from(LocalDateTime.parse(source, formatter).atZone(zoneId).toInstant());
    }
}
