package io.github.bootystar.starter.spring.converter;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * DateTimeFormat注解的的转化器
 * 
 *
 * @author bootystar
 */
public interface DateTimeConverter<S, T> extends ConditionalConverter, Converter<S, T> {

    @Override
    default boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return !targetType.hasAnnotation(DateTimeFormat.class);
    }

}
