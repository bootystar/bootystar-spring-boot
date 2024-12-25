package io.github.bootystar.starter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author bootystar
 */
@Data
@ConfigurationProperties(prefix = "bootystar")
public class BootystarProperties {
    private String timeFormat = "HH:mm:ss";
    private String dateFormat = "yyyy-MM-dd";
    private String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
}
