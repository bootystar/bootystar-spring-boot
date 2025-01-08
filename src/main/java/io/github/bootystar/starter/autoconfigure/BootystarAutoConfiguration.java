package io.github.bootystar.starter.autoconfigure;

import io.github.bootystar.starter.autoconfigure.suppoert.*;
import io.github.bootystar.starter.prop.BootystarProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

/**
 * 自动配置类
 * @author bootystar
 */
@AutoConfiguration
@EnableConfigurationProperties(BootystarProperties.class)
@Import({
        AopConfiguration.class,
        ConverterConfiguration.class,
        JacksonConfiguration.class,
        MybatisPlusConfiguration.class,
        RedisConfiguration.class
})
public class BootystarAutoConfiguration {


}
