package io.github.bootystar.autoconfigure.core;

import io.github.bootystar.autoconfigure.BootystarProperties;
import io.github.bootystar.autoconfigure.prop.ConverterProperties;
import io.github.bootystar.autoconfigure.spring.converter.support.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;


/**
 * bootystar转换器自动配置
 *
 * @author bootystar
 */
@Slf4j
@AutoConfiguration
@ConditionalOnClass(org.springframework.core.convert.converter.Converter.class)
@EnableConfigurationProperties(ConverterProperties.class)
@ConditionalOnProperty(prefix = "bootystar.converter", name = "enabled", havingValue = "true", matchIfMissing = true)
public class BootystarConverterAutoConfiguration {

    @Bean
    public String2DateConverter string2DateConverter(BootystarProperties properties) {
        log.debug("String2DateConverter Configured");
        return new String2DateConverter(properties.getDateTimeFormat(), properties.getTimeZone());
    }

    @Bean
    public String2LocalDateTimeConverter string2LocalDateTimeConverter(BootystarProperties properties) {
        log.debug("String2LocalDateTimeConverter Configured");
        return new String2LocalDateTimeConverter(properties.getDateTimeFormat());
    }

    @Bean
    public String2LocalDateConverter string2LocalDateConverter(BootystarProperties properties) {
        log.debug("String2LocalDateConverter configured");
        return new String2LocalDateConverter(properties.getDateFormat());
    }

    @Bean
    public String2LocalTimeConverter string2LocalTimeConverter(BootystarProperties properties) {
        log.debug("String2LocalTimeConverter Configured");
        return new String2LocalTimeConverter(properties.getTimeFormat());
    }

    @Bean
    public String2SqlDateConverter string2SqlDateConverter(BootystarProperties properties) {
        log.debug("String2SqlDateConverter Configured");
        return new String2SqlDateConverter(properties.getDateFormat());
    }

    @Bean
    public String2SqlTimeConverter string2SqlTimeConverter(BootystarProperties properties) {
        log.debug("String2SqlTimeConverter Configured");
        return new String2SqlTimeConverter(properties.getTimeFormat());
    }

    @Bean
    public String2SqlTimestampConverter string2SqlTimestampConverter(BootystarProperties properties) {
        log.debug("String2SqlTimestampConverter Configured");
        return new String2SqlTimestampConverter(properties.getDateTimeFormat());
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
