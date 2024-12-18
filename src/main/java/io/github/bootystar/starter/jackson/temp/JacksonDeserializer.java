//package io.github.bootystar.autoconfigure.databind.jackson.temp;
//
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
//
//import java.io.IOException;
//import java.util.function.Function;
//
///**
// * @author booty
// */
//public class JacksonDeserializer<T> extends StdScalarDeserializer<T> {
//    private final Function<String, T> policy;
//
//    public JacksonDeserializer(Class<T> clazz, Function<String, T> policy) {
//        super(clazz);
//        this.policy = policy;
//    }
//
//    @Override
//    public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
//        return policy.apply(p.getText());
//    }
//}
