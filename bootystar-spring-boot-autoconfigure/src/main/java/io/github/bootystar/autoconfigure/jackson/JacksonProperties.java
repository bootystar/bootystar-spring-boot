package io.github.bootystar.autoconfigure.jackson;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Jackson properties
 * @author bootystar
 */
@Data
@ConfigurationProperties("bootystar.jackson")
public class JacksonProperties {
    /**
     * Whether to enable Long to string serialization
     */
    private boolean longToString = true;
    /**
     * Whether to enable Double to string serialization
     */
    private boolean doubleToString = true;
    /**
     * Whether to enable BigInteger to string serialization
     */
    private boolean bigIntegerToString = true;
    /**
     * Whether to enable BigDecimal to string serialization
     */
    private boolean bigDecimalToString = true;
}