package io.github.bootystar.starter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author bootystar
 */
@Data
@ConfigurationProperties(prefix = "bootystar")
public class BootystarProperties {

    static class Date {
        private boolean enable;
        private String format;
    }

}
