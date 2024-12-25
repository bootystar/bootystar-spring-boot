package io.github.bootystar.starter.spring.converter;

import io.github.bootystar.starter.helper.DateHelper;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author bootystar
 * @since 2023/10/27
 */
public class String2DateConverter implements Converter<String, Date> {

    @Override
    @SneakyThrows
    public Date convert(String source) {
        return DateHelper.string2Date(source);
    }

}
