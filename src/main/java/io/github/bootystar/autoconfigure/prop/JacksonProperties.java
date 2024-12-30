package io.github.bootystar.autoconfigure.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author bootystar
 */
@Data
@ConfigurationProperties(prefix = "bootystar.jackson")
public class JacksonProperties {
    private boolean enabled = true;
    private boolean longToString = true;
    private boolean doubleToString = true;
    private boolean bigIntegerToString = true;
    private boolean bigDecimalToString = true;
}
