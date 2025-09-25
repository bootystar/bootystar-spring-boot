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
    /**
     * long序列化为字符串
     */
    private boolean longToString = true;
    /**
     * double序列化为字符串
     */
    private boolean doubleToString = true;
    /**
     * bigInteger序列化为字符串
     */
    private boolean bigIntegerToString = true;
    /**
     * bigDecimal序列化为字符串
     */
    private boolean bigDecimalToString = true;
}
