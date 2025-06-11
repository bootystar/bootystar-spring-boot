package io.github.bootystar.starter.autoconfigure.suppoert;

import io.github.bootystar.starter.prop.BootystarProperties;
import io.github.bootystar.starter.spring.converter.support.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 转换器配置
 *
 * @author bootystar
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = "bootystar.converter", name = "enabled", havingValue = "true", matchIfMissing = true)
public class ConverterConfiguration {

    @ConditionalOnProperty(prefix = "bootystar.converter", name = "string-to-date", havingValue = "true", matchIfMissing = true)
    @Configuration(proxyBeanMethods = false)
    static class String2DateConverterConfiguration {
        @Bean
        public String2DateConverter string2DateConverter(BootystarProperties properties) {
            log.debug("String2DateConverter Configured");
            return new String2DateConverter(properties.getDateTimeFormat(), properties.getTimeZoneId());
        }
    }

    @ConditionalOnProperty(prefix = "bootystar.converter", name = "string-to-local-date-time", havingValue = "true", matchIfMissing = true)
    @Configuration(proxyBeanMethods = false)
    static class String2LocalDateTimeConverterConfiguration {
        @Bean
        public String2LocalDateTimeConverter string2LocalDateTimeConverter(BootystarProperties properties) {
            log.debug("String2LocalDateTimeConverter Configured");
            return new String2LocalDateTimeConverter(properties.getDateTimeFormat());
        }
    }

    @ConditionalOnProperty(prefix = "bootystar.converter", name = "string-to-local-date", havingValue = "true", matchIfMissing = true)
    @Configuration(proxyBeanMethods = false)
    static class String2LocalDateConverterConfiguration {
        @Bean
        public String2LocalDateConverter string2LocalDateConverter(BootystarProperties properties) {
            log.debug("String2LocalDateConverter configured");
            return new String2LocalDateConverter(properties.getDateFormat());
        }
    }

    @ConditionalOnProperty(prefix = "bootystar.converter", name = "string-to-local-time", havingValue = "true", matchIfMissing = true)
    @Configuration(proxyBeanMethods = false)
    static class String2LocalTimeConverterConfiguration {
        @Bean
        public String2LocalTimeConverter string2LocalTimeConverter(BootystarProperties properties) {
            log.debug("String2LocalTimeConverter Configured");
            return new String2LocalTimeConverter(properties.getTimeFormat());
        }
    }

    @ConditionalOnProperty(prefix = "bootystar.converter", name = "string-to-sql-date", havingValue = "true", matchIfMissing = true)
    @Configuration(proxyBeanMethods = false)
    static class String2SqlDateConverterConfiguration {
        @Bean
        public String2SqlDateConverter string2SqlDateConverter(BootystarProperties properties) {
            log.debug("String2SqlDateConverter Configured");
            return new String2SqlDateConverter(properties.getDateFormat());
        }
    }

    @ConditionalOnProperty(prefix = "bootystar.converter", name = "string-to-sql-time", havingValue = "true", matchIfMissing = true)
    @Configuration(proxyBeanMethods = false)
    static class String2SqlTimeConverterConfiguration {
        @Bean
        public String2SqlTimeConverter string2SqlTimeConverter(BootystarProperties properties) {
            log.debug("String2SqlTimeConverter Configured");
            return new String2SqlTimeConverter(properties.getTimeFormat());
        }
    }

    @ConditionalOnProperty(prefix = "bootystar.converter", name = "string-to-sql-timestamp", havingValue = "true", matchIfMissing = true)
    @Configuration(proxyBeanMethods = false)
    static class String2SqlTimestampConverterConfiguration {
        @Bean
        public String2SqlTimestampConverter string2SqlTimestampConverter(BootystarProperties properties) {
            log.debug("String2SqlTimestampConverter Configured");
            return new String2SqlTimestampConverter(properties.getDateTimeFormat());
        }
    }

}
