package io.github.bootystar.starter;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * bootystar自动配置
 *
 * @author bootystar
 */
@AutoConfiguration
@EnableConfigurationProperties(BootystarProperties.class)
public class BootystarAutoConfiguration {

}
