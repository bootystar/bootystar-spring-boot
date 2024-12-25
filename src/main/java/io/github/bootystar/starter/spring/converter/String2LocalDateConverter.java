package io.github.bootystar.starter.spring.converter;

import io.github.bootystar.starter.helper.DateHelper;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author bootystar
 * @since 2023/10/27
 */
public class String2LocalDateConverter implements Converter<String, LocalDate> {

    @Override
    public LocalDate convert(String source) {
        return DateHelper.string2LocalDate(source);
    }
}
