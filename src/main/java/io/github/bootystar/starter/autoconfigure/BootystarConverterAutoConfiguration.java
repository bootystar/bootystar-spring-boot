package io.github.bootystar.starter.autoconfigure;

import io.github.bootystar.starter.constants.DateConst;
import io.github.bootystar.starter.spring.converter.String2LocalDateConverter;
import io.github.bootystar.starter.spring.converter.String2LocalDateTimeConverter;
import io.github.bootystar.starter.spring.converter.String2LocalTimeConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;


/**
 * bootystar转换器自动配置
 *
 * @author bootystar
 */
@Slf4j
@AutoConfiguration
@ConditionalOnClass(org.springframework.core.convert.converter.Converter.class)
public class BootystarConverterAutoConfiguration {

    @Bean
    public String2LocalDateTimeConverter string2LocalDateTimeConverter() {
        log.debug("String2LocalDateTimeConverter Configured");
        return new String2LocalDateTimeConverter(DateConst.DEFAULT_DATETIME_PATTERN);
    }

    @Bean
    public String2LocalDateConverter string2LocalDateConverter() {
        log.debug("String2LocalDateConverter configured");
        return new String2LocalDateConverter(DateConst.DEFAULT_DATE_FORMAT);
    }

    @Bean
    public String2LocalTimeConverter string2LocalTimeConverter() {
        log.debug("String2LocalTimeConverter Configured");
        return new String2LocalTimeConverter(DateConst.DEFAULT_TIME_FORMAT);
    }
}
