package io.github.bootystar.starter.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author bootystar
 */
public abstract class ObjectMapperFactory {

    public static ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper;
    }
}
