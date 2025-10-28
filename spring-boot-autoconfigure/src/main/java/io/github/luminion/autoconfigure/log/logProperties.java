package io.github.luminion.autoconfigure.log;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author luminion
 */
@Data
@ConfigurationProperties("luminion.log")
public class logProperties {
}
