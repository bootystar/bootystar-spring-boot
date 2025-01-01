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
            return new String2DateConverter(properties.getDateTimeFormat(), properties.getTimeZone());
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

//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        // Converter兼容@DateTimeFormat 原converter优先级低下使用配置开关处
        /*

        {@link org.springframework.core.convert.support.GenericConversionService}
        源码调用链
        GenericConversionService.java:190 根据原始类型和目标类型获取converter
        GenericConversionService.java:255 创建ConverterCacheKey, 从缓存中获取, 有则返回
            GenericConverter converter = this.converterCache.get(key);
        GenericConversionService.java:260 缓存中没有, 则寻找并放入cothis.converterCache
            converter = this.converters.find(sourceType, targetType);
        GenericConversionService.java:544 双循环遍历原始类型及目标类型, 匹配则返回
            GenericConverter converter = getRegisteredConverter(sourceType, targetType, convertiblePair);
        GenericConversionService.java:554 从this.converters中获取, 有则返回(ConvertersForPair为一内部类)
           GenericConverter converter = convertersForPair.getConverter(sourceType, targetType);
        GenericConversionService.java:661 从ConvertersForPair内部类中的this.converters中获取, 有则返回
            private final Deque<GenericConverter> converters = new ConcurrentLinkedDeque<>();
            for (GenericConverter converter : this.converters) {
				if (!(converter instanceof ConditionalGenericConverter) ||
						((ConditionalGenericConverter) converter).matches(sourceType, targetType)) {
					return converter;
				}
			}
			ConvertersForPair内部类中的this.converters中存放的是SpringBoot自动配置的converter
            如: StringToNumberConverterFactory, StringToEnumConverterFactory, StringToCollectionConverterFactory等
            其为一个队列,
            由于自定义的converter优先级高于SpringBoot自动配置的converter, 所以会先一步被放入队列中
            所以一旦存在自定义的converter, 先调用的一定是自定义的converter,
            不会再调用SpringBoot自动配置的注解处理器org.springframework.format.support.FormattingConversionService$AnnotationParserConverter
            同理, 若降低了自定义配置的优先级, 找到了SpringBoot自动配置的converter则不会再调用自定义的converter,
            因机制为单次converter调用, 多converter无法做到兼容@DateTimeFormat,
            只能通过实现ConditionalConverter接口, 在match方法中判断无注解时启用自己的converter, 有注解时跳过
                if (!(converter instanceof ConditionalGenericConverter) ||
						((ConditionalGenericConverter) converter).matches(sourceType, targetType)) {
					return converter;
				}

            GenericConversionService.java:340  私有类ConverterAdapter, 可以将Converter适配为ConditionalGenericConverter

        */
////        FormatterRegistry bean = applicationContext.getBean(FormatterRegistry.class);
////        bean.addConverter(new String2LocalTimeConverter());
////        bean.addConverter(new String2LocalDateConverter());
////        bean.addConverter(new String2LocalDateTimeConverter());
////        bean.addConverter(new String2DateConverter());
////        ConversionService bean1 = applicationContext.getBean(ConversionService.class);
////        ConversionService bean2 = applicationContext.getBean(WebConversionService.class);
//
//    }
}
