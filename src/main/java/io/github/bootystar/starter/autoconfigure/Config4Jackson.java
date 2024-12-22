package io.github.bootystar.starter.autoconfigure;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.autoconfigure.http.GsonHttpMessageConvertersConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.http.JacksonHttpMessageConvertersConfiguration;
import org.springframework.boot.autoconfigure.http.JsonbHttpMessageConvertersConfiguration;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonProperties;
import org.springframework.boot.autoconfigure.jsonb.JsonbAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;


@AutoConfiguration
@ConditionalOnClass(ObjectMapper.class)
public class Config4Jackson {

    private static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    private static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";


    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(Jackson2ObjectMapperBuilder.class)
    @EnableConfigurationProperties(JacksonProperties.class)
    @AutoConfigureBefore(JacksonAutoConfiguration.class)
    static class Jackson2ObjectMapperBuilderCustomizerConfiguration {

        @Bean
        @ConditionalOnMissingBean(Jackson2ObjectMapperBuilderCustomizer.class)
        public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
            log.debug("Jackson2ObjectMapperBuilderCustomizer Configured");
            return builder -> {
                builder
                        // 序列化时，对象为 null，是否抛异常
                        .failOnEmptyBeans(false)
                        // 反序列化时，json 中包含 pojo 不存在属性时，是否抛异常
                        .failOnUnknownProperties(false)
                        // 禁止将 java.util.Date、Calendar 序列化为数字(时间戳)
                        .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                        // 设置 java.util.Date, Calendar 序列化、反序列化的格式
                        .dateFormat(new SimpleDateFormat(DEFAULT_DATETIME_PATTERN))
                        // 设置 java.util.Date, Calendar 序列化、反序列化的时区
                        .timeZone(TimeZone.getTimeZone("GMT+8"))
//                    // null 不参与序列化
//                    .serializationInclusion(JsonInclude.Include.NON_NULL)
                ;

                // Jackson 序列化 long类型为String，解决后端返回的Long类型在前端精度丢失的问题
                builder.serializerByType(BigInteger.class, ToStringSerializer.instance);
                builder.serializerByType(Long.class, ToStringSerializer.instance);
                builder.serializerByType(Long.TYPE, ToStringSerializer.instance);

                // 配置 Jackson 反序列化 LocalDateTime、LocalDate、LocalTime 时使用的格式
                builder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATETIME_PATTERN)));
                builder.deserializerByType(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
                builder.deserializerByType(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));

                // 配置 Jackson 序列化 LocalDateTime、LocalDate、LocalTime 时使用的格式
                builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATETIME_PATTERN)));
                builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
                builder.serializers(new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));

            };
        }
    }


    @Bean
    @ConditionalOnMissingBean(MappingJackson2HttpMessageConverter.class)
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();

        // 忽略json字符串中不识别的属性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 忽略无法转换的对象
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // PrettyPrinter 格式化输出
//        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        // NULL不参与序列化
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // 指定时区
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        // 日期类型字符串处理
        objectMapper.setDateFormat(new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT));

        // java8日期日期处理
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));

        objectMapper.registerModule(javaTimeModule);

        // long转string, 避免前端精度丢失
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);

        converter.setObjectMapper(objectMapper);
        return converter;
    }


    @Configuration(proxyBeanMethods = false)
    @ConditionalOnBean(Jackson2ObjectMapperBuilder.class)
    @ConditionalOnClass(HttpMessageConverter.class)
    @AutoConfiguration(
            after = { GsonAutoConfiguration.class, JacksonAutoConfiguration.class, JsonbAutoConfiguration.class }
            ,before ={HttpMessageConvertersAutoConfiguration.class}
    )
//    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    static class MappingJackson2HttpMessageConverterConfiguration {

        @Bean
        @ConditionalOnMissingBean(MappingJackson2HttpMessageConverter.class)
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(Jackson2ObjectMapperBuilder builder) {
            ObjectMapper objectMapper = builder.createXmlMapper(false).build();
            return new MappingJackson2HttpMessageConverter(objectMapper);
        }

    }

//    @Configuration(proxyBeanMethods = false)
//    @ConditionalOnClass(XmlMapper.class)
//    @ConditionalOnBean(Jackson2ObjectMapperBuilder.class)
//    protected static class MappingJackson2XmlHttpMessageConverterConfiguration {
//
//        @Bean
//        @ConditionalOnMissingBean
//        public MappingJackson2XmlHttpMessageConverter mappingJackson2XmlHttpMessageConverter(
//                Jackson2ObjectMapperBuilder builder) {
//            return new MappingJackson2XmlHttpMessageConverter(builder.createXmlMapper(true).build());
//        }
//
//    }

}
