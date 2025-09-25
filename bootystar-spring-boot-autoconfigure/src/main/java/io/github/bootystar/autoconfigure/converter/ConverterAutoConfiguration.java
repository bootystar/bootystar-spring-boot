package io.github.bootystar.autoconfigure.converter;

import io.github.bootystar.autoconfigure.DateTimeFormatProperties;
import io.github.bootystar.autoconfigure.converter.support.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 转换器配置
 *
 * @author bootystar
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({DateTimeFormatProperties.class})
@ConditionalOnProperty(value = "bootystar.converter.enabled", havingValue = "true", matchIfMissing = true)
public class ConverterAutoConfiguration {

    @ConditionalOnProperty(value = "bootystar.converter.string-to-date", havingValue = "true", matchIfMissing = true)
    @Configuration(proxyBeanMethods = false)
    static class String2DateConverterConfiguration {
        @Bean
        public String2DateConverter string2DateConverter(DateTimeFormatProperties properties) {
            log.debug("String2DateConverter Configured");
            return new String2DateConverter(properties.getDateTime(), properties.getTimeZone());
        }
    }

    @ConditionalOnProperty(value = "bootystar.converter.string-to-local-date-time", havingValue = "true", matchIfMissing = true)
    @Configuration(proxyBeanMethods = false)
    static class String2LocalDateTimeConverterConfiguration {
        @Bean
        public String2LocalDateTimeConverter string2LocalDateTimeConverter(DateTimeFormatProperties properties) {
            log.debug("String2LocalDateTimeConverter Configured");
            return new String2LocalDateTimeConverter(properties.getDateTime());
        }
    }

    @ConditionalOnProperty(value = "bootystar.converter.string-to-local-date", havingValue = "true", matchIfMissing = true)
    @Configuration(proxyBeanMethods = false)
    static class String2LocalDateConverterConfiguration {
        @Bean
        public String2LocalDateConverter string2LocalDateConverter(DateTimeFormatProperties properties) {
            log.debug("String2LocalDateConverter configured");
            return new String2LocalDateConverter(properties.getDate());
        }
    }

    @ConditionalOnProperty(value = "bootystar.converter.string-to-local-time", havingValue = "true", matchIfMissing = true)
    @Configuration(proxyBeanMethods = false)
    static class String2LocalTimeConverterConfiguration {
        @Bean
        public String2LocalTimeConverter string2LocalTimeConverter(DateTimeFormatProperties properties) {
            log.debug("String2LocalTimeConverter Configured");
            return new String2LocalTimeConverter(properties.getTime());
        }
    }

    @ConditionalOnProperty(value = "bootystar.converter.string-to-sql-date", havingValue = "true", matchIfMissing = true)
    @Configuration(proxyBeanMethods = false)
    static class String2SqlDateConverterConfiguration {
        @Bean
        public String2SqlDateConverter string2SqlDateConverter(DateTimeFormatProperties properties) {
            log.debug("String2SqlDateConverter Configured");
            return new String2SqlDateConverter(properties.getDate());
        }
    }

    @ConditionalOnProperty(value = "bootystar.converter.string-to-sql-time", havingValue = "true", matchIfMissing = true)
    @Configuration(proxyBeanMethods = false)
    static class String2SqlTimeConverterConfiguration {
        @Bean
        public String2SqlTimeConverter string2SqlTimeConverter(DateTimeFormatProperties properties) {
            log.debug("String2SqlTimeConverter Configured");
            return new String2SqlTimeConverter(properties.getTime());
        }
    }

    @ConditionalOnProperty(value = "bootystar.converter.string-to-sql-timestamp", havingValue = "true", matchIfMissing = true)
    @Configuration(proxyBeanMethods = false)
    static class String2SqlTimestampConverterConfiguration {
        @Bean
        public String2SqlTimestampConverter string2SqlTimestampConverter(DateTimeFormatProperties properties) {
            log.debug("String2SqlTimestampConverter Configured");
            return new String2SqlTimestampConverter(properties.getDateTime());
        }
    }

}
