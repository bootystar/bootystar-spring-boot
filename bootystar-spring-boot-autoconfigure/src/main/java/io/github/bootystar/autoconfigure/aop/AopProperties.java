package io.github.bootystar.autoconfigure.aop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * @author bootystar
 */
@ConfigurationProperties("bootystar.aop")
@Data
public class AopProperties {
    /**
     * weather to enable AopAutoConfiguration
     */
    private boolean auto = true;
}
