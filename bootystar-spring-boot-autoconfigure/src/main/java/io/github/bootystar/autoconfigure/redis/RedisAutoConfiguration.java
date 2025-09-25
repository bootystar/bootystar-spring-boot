package io.github.bootystar.autoconfigure.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis配置
 * @author bootystar
 * @see org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(RedisOperations.class)
@ConditionalOnProperty(value = "bootystar.redis.enabled", havingValue = "true", matchIfMissing = true)
public class RedisAutoConfiguration {

//    @Bean // todo 添加安全的序列化方式
//    public RedisSerializer<Object> redisSerializer(Jackson2ObjectMapperBuilder builder) {
//        log.debug("RedisSerializer Configured");
//        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
//        //启用反序列化所需的类型信息,在属性中添加@class
//        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
//        //配置null值的序列化器
//        GenericJackson2JsonRedisSerializer.registerNullValueSerializer(objectMapper, null);
//        return new GenericJackson2JsonRedisSerializer(objectMapper);
//    }
//
//    @Bean
//    @ConditionalOnMissingBean(name = "redisTemplate")
//    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory, RedisSerializer<Object> redisSerializer) {
//        log.debug("RedisTemplate Configured");
//        RedisTemplate<Object, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(redisConnectionFactory);
//        template.setDefaultSerializer(redisSerializer);
//        template.setValueSerializer(redisSerializer);
//        template.setHashValueSerializer(redisSerializer);
//        template.setKeySerializer(StringRedisSerializer.UTF_8);
//        template.setHashKeySerializer(StringRedisSerializer.UTF_8);
//        template.afterPropertiesSet();
//        return template;
//    }

    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        log.debug("RedisTemplate Configured");

        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        StringRedisSerializer stringSerializer = new StringRedisSerializer();
        GenericJackson2JsonRedisSerializer jacksonSerializer = new GenericJackson2JsonRedisSerializer();

        template.setKeySerializer(stringSerializer);
        template.setHashKeySerializer(stringSerializer);
        template.setValueSerializer(jacksonSerializer);
        template.setHashValueSerializer(jacksonSerializer);
        template.setDefaultSerializer(jacksonSerializer);

        template.afterPropertiesSet();
        return template;
    }

}