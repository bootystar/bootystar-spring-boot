package io.github.bootystar.starter.autoconfigure;

import io.github.bootystar.starter.spring.converter.String2LocalDateConverter;
import io.github.bootystar.starter.spring.converter.String2LocalDateTimeConverter;
import io.github.bootystar.starter.spring.converter.String2LocalTimeConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

/**
 * @author bootystar
 */
@Slf4j
@AutoConfiguration
@ConditionalOnClass(org.springframework.core.convert.converter.Converter.class)
public class Config4Converter {

    private static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    private static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";


    @Bean
    public String2LocalDateTimeConverter string2LocalDateTimeConverter() {
        log.debug("String2LocalDateTimeConverter Configured");
        return new String2LocalDateTimeConverter(DEFAULT_DATETIME_PATTERN);
    }

    @Bean
    public String2LocalDateConverter string2LocalDateConverter() {
        log.debug("String2LocalDateConverter configured");
        return new String2LocalDateConverter(DEFAULT_DATE_FORMAT);
    }

    @Bean
    public String2LocalTimeConverter string2LocalTimeConverter() {
        log.debug("String2LocalTimeConverter Configured");
        return new String2LocalTimeConverter(DEFAULT_TIME_FORMAT);
    }
}
