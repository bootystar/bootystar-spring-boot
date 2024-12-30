package io.github.bootystar.autoconfigure.spring.converter;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 兼容性{@link DateTimeFormat}的转化器
 * <p>
 * 源码位置{@link org.springframework.core.convert.support.GenericConversionService.ConverterAdapter#matches(TypeDescriptor, TypeDescriptor)}
 *
 * @author bootystar
 */
public interface DateTimeConverter<S, T> extends ConditionalConverter, Converter<S, T> {

    @Override
    default boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return !targetType.hasAnnotation(DateTimeFormat.class);
    }

}
