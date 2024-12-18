//package io.github.bootystar.autoconfigure.databind.jackson.temp;
//
//import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
//
//import java.lang.annotation.*;
//import java.util.function.Function;
//
///**
// * @author bootystar
// */
//@Target(ElementType.FIELD)
//@Retention(RetentionPolicy.RUNTIME)
//@Documented
//@JacksonAnnotationsInside
//public @interface JsonOut {
//
//    Class<? extends Function<Object, String>> value();
////    Class<? extends Function<?, String>> value() default Fallback.class;
////
////    class Fallback implements Function<Object,String> {
////
////        @Override
////        public String apply(Object o) {
////            if (o==null) return null;
////            String source = String.valueOf(o);
////            if (source == null || source.isEmpty()) return source;
////            if (source.length() == 1) return "*";
////            if (source.length() == 2) return source.charAt(0) + "*";
////            int index = source.length() / 3;
////            int last = source.length() % 3;
////            StringBuilder repeatedPart = new StringBuilder();
////            for (int i = 0; i < index + last; i++) {
////                repeatedPart.append("*");
////            }
////            return source.substring(0, index) + repeatedPart + source.substring(source.length() - index);
////        }
////    }
//
//
//}