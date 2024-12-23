package io.github.bootystar.starter.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * bootystar自动配置
 *
 * @author bootystar
 */
@AutoConfiguration
@Import({
        BootystarConverterAutoConfiguration.class,
        BootystarJacksonAutoConfiguration.class,
        BootystarAopAutoConfiguration.class,
        BootystarMybatisPlusAutoConfiguration.class,
        BootystarRedisAutoConfiguration.class
})
public class BootystarAutoConfiguration {
}
