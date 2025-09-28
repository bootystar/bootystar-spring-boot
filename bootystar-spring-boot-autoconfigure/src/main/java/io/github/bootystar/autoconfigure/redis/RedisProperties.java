package io.github.bootystar.autoconfigure.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Redis配置属性
 * @author bootystar
 */
@Data
@ConfigurationProperties("bootystar.redis")
public class RedisProperties {
    /**
     * 是否启用redis自动配置，默认true
     */
    private boolean auto = true;

}