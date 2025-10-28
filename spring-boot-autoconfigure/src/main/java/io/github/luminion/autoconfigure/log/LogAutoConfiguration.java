package io.github.luminion.autoconfigure.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author luminion
 */
@Slf4j
@AutoConfiguration
@EnableConfigurationProperties({logProperties.class})
public class LogAutoConfiguration {
}
