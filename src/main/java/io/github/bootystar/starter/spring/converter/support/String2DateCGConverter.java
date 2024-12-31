//package io.github.bootystar.starter.spring.converter.support;
//
//import org.springframework.core.convert.TypeDescriptor;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.time.format.DateTimeFormatter;
//import java.util.Date;
//
///**
// * @author bootystar
// * @since 2023/10/27
// */
//public class String2DateCGConverter extends AbstractCGConverter {
//    private final DateTimeFormatter formatter;
//    private final ZoneId zoneId;
//
//    public String2DateCGConverter(String pattern, String zoneId) {
//        super(String.class, Date.class);
//        this.formatter = DateTimeFormatter.ofPattern(pattern);
//        this.zoneId = ZoneId.of(zoneId);
//    }
//
//    @Override
//    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
//        if (source == null) {
//            return null;
//        }
//        String sourceStr = (String) source;
//        if (sourceStr.isEmpty()) {
//            return null;
//        }
//        return Date.from(LocalDateTime.parse(sourceStr, formatter).atZone(zoneId).toInstant());
//    }
//
//}
