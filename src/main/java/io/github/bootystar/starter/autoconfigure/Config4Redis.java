package io.github.bootystar.starter.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

/**
 * todo 配置Redis
 * https://juejin.im/post/5e869d426fb9a03c6148c97e
 * @author bootystar
 */
@Slf4j
@ConditionalOnClass({RedisTemplate.class, RedisAutoConfiguration.class, ObjectMapper.class})
@AutoConfiguration(after = RedisAutoConfiguration.class)
public class Config4Redis {

    @Bean
    @ConditionalOnBean({ObjectMapper.class, RedisTemplate.class})
    public RedisTemplate<Object, Object> redisTemplate(RedisTemplate<Object, Object> redisTemplate, ObjectMapper objectMapper) {
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(objectMapper);
        redisTemplate.setDefaultSerializer(serializer);
        return redisTemplate;
    }

}
