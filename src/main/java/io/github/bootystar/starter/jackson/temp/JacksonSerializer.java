//package io.github.bootystar.autoconfigure.databind.jackson.temp;
//
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.databind.SerializerProvider;
//import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
//
//import java.io.IOException;
//import java.util.function.Function;
//
///**
// * @author bootystar
// */
//public class JacksonSerializer<T> extends StdScalarSerializer<T> {
//
//    private final Function<T, String> policy;
//
//    public JacksonSerializer(Class<T> clazz, Function<T, String> policy) {
//        super(clazz);
//        this.policy = policy;
//    }
//
//    @Override
//    public void serialize(T value, JsonGenerator gen, SerializerProvider provider) throws IOException {
//        gen.writeString(policy.apply(value));
//    }
//}
