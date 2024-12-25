package io.github.bootystar.starter.autoconfigure;

import io.github.bootystar.starter.helper.DateHelper;
import io.github.bootystar.starter.spring.converter.String2DateConverter;
import io.github.bootystar.starter.spring.converter.String2LocalDateConverter;
import io.github.bootystar.starter.spring.converter.String2LocalDateTimeConverter;
import io.github.bootystar.starter.spring.converter.String2LocalTimeConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.web.format.WebConversionService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.FormatterRegistrar;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * bootystar转换器自动配置
 *
 * @author bootystar
 */
@Slf4j
@AutoConfiguration
@ConditionalOnClass(org.springframework.core.convert.converter.Converter.class)
public class BootystarConverterAutoConfiguration implements ApplicationContextAware {

    @Bean
    public String2DateConverter string2DateConverter() {
        log.debug("String2DateConverter Configured");
        return new String2DateConverter();
    }

    @Bean
    public String2LocalDateTimeConverter string2LocalDateTimeConverter() {
        log.debug("String2LocalDateTimeConverter Configured");
        return new String2LocalDateTimeConverter();
    }

    @Bean
    public String2LocalDateConverter string2LocalDateConverter() {
        log.debug("String2LocalDateConverter configured");
        return new String2LocalDateConverter();
    }

    @Bean
    public String2LocalTimeConverter string2LocalTimeConverter() {
        log.debug("String2LocalTimeConverter Configured");
        return new String2LocalTimeConverter();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // todo Converter兼容@DateTimeFormat
//        FormatterRegistry bean = applicationContext.getBean(FormatterRegistry.class);
//        bean.addConverter(new String2LocalTimeConverter());
//        bean.addConverter(new String2LocalDateConverter());
//        bean.addConverter(new String2LocalDateTimeConverter());
//        bean.addConverter(new String2DateConverter());
//        ConversionService bean1 = applicationContext.getBean(ConversionService.class);
//        ConversionService bean2 = applicationContext.getBean(WebConversionService.class);

    }
}
