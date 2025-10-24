package io.github.luxmixus.autoconfigure.converter.support;

import io.github.luxmixus.autoconfigure.converter.DateTimeConverter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author luxmixus
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
