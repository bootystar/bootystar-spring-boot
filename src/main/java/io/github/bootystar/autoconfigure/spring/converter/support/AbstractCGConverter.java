//package io.github.bootystar.starter.spring.converter.support;
//
//import org.springframework.core.convert.TypeDescriptor;
//import org.springframework.core.convert.converter.ConditionalGenericConverter;
//import org.springframework.format.annotation.DateTimeFormat;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.Collections;
//import java.util.Date;
//import java.util.Set;
//
///**
// * ConditionalGenericConverter 允许根据判断, 进行类型转化
// *
// * @author bootystar
// */
//public abstract class AbstractCGConverter implements ConditionalGenericConverter {
//    protected final Class<?> sourceType;
//    protected final Class<?> targetType;
//
//    public AbstractCGConverter(Class<?> sourceType, Class<?> targetType) {
//        this.sourceType = sourceType;
//        this.targetType = targetType;
//    }
//
//    @Override
//    public Set<ConvertiblePair> getConvertibleTypes() {
//        return Collections.singleton(new ConvertiblePair(this.sourceType, this.targetType));
//    }
//
//    @Override
//    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
//        return this.sourceType.equals(sourceType.getObjectType())
//                && this.targetType.equals(targetType.getObjectType())
//                && !targetType.hasAnnotation(DateTimeFormat.class)
//                ;
//    }
//
////    @Override
////    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
////        return null;
////    }
//
//}
