package io.github.bootystar.autoconfigure.prop.support;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author bootystar
 */
@Data
public class MybatisPlusProperties {
    private boolean enabled = true;
}
