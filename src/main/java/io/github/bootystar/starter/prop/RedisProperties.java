package io.github.bootystar.starter.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author bootystar
 */
@Data
@ConfigurationProperties(prefix = "bootystar.redis")
public class RedisProperties {
    private boolean enabled = true;
}
