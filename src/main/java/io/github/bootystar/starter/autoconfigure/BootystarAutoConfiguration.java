package io.github.bootystar.starter.autoconfigure;

import io.github.bootystar.starter.autoconfigure.suppoert.BootystarConverterAutoConfiguration;
import io.github.bootystar.starter.autoconfigure.suppoert.BootystarJacksonAutoConfiguration;
import io.github.bootystar.starter.autoconfigure.suppoert.BootystarMybatisPlusAutoConfiguration;
import io.github.bootystar.starter.autoconfigure.suppoert.BootystarRedisAutoConfiguration;
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
        BootystarConverterAutoConfiguration.class,
        BootystarConverterAutoConfiguration.class,
        BootystarJacksonAutoConfiguration.class,
        BootystarMybatisPlusAutoConfiguration.class,
        BootystarRedisAutoConfiguration.class
})
public class BootystarAutoConfiguration {


}
