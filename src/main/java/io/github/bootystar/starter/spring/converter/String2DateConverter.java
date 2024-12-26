package io.github.bootystar.starter.spring.converter;

import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author bootystar
 * @since 2023/10/27
 */
public class String2DateConverter implements Converter<String, Date> {
    private final ZoneId zoneId;
    private final DateTimeFormatter formatter;

    public String2DateConverter(String pattern, String zoneId) {
        this.zoneId = ZoneId.of(zoneId);
        this.formatter = DateTimeFormatter.ofPattern(pattern);
    }

    @Override
    @SneakyThrows
    public Date convert(String source) {
        if (source.isEmpty()) {
            return null;
        }
        return Date.from(LocalDateTime.parse(source,formatter).atZone(zoneId).toInstant());
    }

}
