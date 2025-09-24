package io.github.bootystar.autoconfigure.jackson;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * jackson属性
 * @author bootystar
 */
@Data
@ConfigurationProperties("bootystar.jackson")
public class JacksonProperties {
    private boolean enabled = true;
    private boolean longToString = true;
    private boolean doubleToString = true;
    private boolean bigIntegerToString = true;
    private boolean bigDecimalToString = true;
}
