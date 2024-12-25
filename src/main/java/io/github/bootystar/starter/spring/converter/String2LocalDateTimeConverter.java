package io.github.bootystar.starter.spring.converter;

import io.github.bootystar.starter.helper.DateHelper;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author bootystar
 * @since 2023/10/27
 */
public class String2LocalDateTimeConverter implements Converter<String, LocalDateTime> {

    @Override
    public LocalDateTime convert(String source) {
        return DateHelper.string2LocalDateTime(source);
    }
}
