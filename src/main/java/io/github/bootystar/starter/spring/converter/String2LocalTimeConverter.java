package io.github.bootystar.starter.spring.converter;

import io.github.bootystar.starter.helper.DateHelper;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author bootystar
 * @since 2023/10/27
 */
public class String2LocalTimeConverter implements Converter<String, LocalTime> {

    @Override
    public LocalTime convert(String source) {
        return DateHelper.string2LocalTime(source);
    }

}
