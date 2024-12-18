package io.github.bootystar.starter.jackson.anno;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.bootystar.starter.jackson.deserializer.JsonFieldDeserializer;
import io.github.bootystar.starter.jackson.serializer.JsonFieldSerializer;

import java.lang.annotation.*;
import java.util.function.Function;

/**
 * @author bootystar
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@JacksonAnnotationsInside
@JsonSerialize(using = JsonFieldSerializer.class)
@JsonDeserialize(using = JsonFieldDeserializer.class)
public @interface JsonMask {

    Class<? extends Function<?, String>> serialize() default Fallback.class;

    Class<? extends Function<String, ?>> deserialize() default Fallback.class;

    class Fallback implements Function<String, String> {
        @Override
        public String apply(String s) {
            return s;
        }
    }


}