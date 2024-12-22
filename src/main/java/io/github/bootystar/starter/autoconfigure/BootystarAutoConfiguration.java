package io.github.bootystar.starter.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * @author bootystar
 */
@AutoConfiguration
@Import({
        ConvertAutoConfiguration.class,
        JacksonAutoConfiguration.class,
        MethodLimitAutoConfiguration.class,
        MybatisPlusAutoConfiguration.class,
        RedisAutoConfiguration.class
})
public class BootystarAutoConfiguration {
}
