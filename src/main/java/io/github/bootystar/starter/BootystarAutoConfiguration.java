package io.github.bootystar.starter;

import io.github.bootystar.starter.autoconfigure.BootystarAopAutoConfiguration;
import io.github.bootystar.starter.autoconfigure.BootystarConverterAutoConfiguration;
import io.github.bootystar.starter.autoconfigure.BootystarJacksonAutoConfiguration;
import io.github.bootystar.starter.autoconfigure.BootystarMybatisPlusAutoConfiguration;
import io.github.bootystar.starter.autoconfigure.BootystarRedisAutoConfiguration;
import io.github.bootystar.starter.prop.DatabindProp;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@EnableConfigurationProperties({BootystarProperties.class, DatabindProp.class})
public class BootystarAutoConfiguration {

}
