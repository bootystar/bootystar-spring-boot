package io.github.bootystar.autoconfigure;

import io.github.bootystar.autoconfigure.aop.AopConfiguration;
import io.github.bootystar.autoconfigure.converter.ConverterConfiguration;
import io.github.bootystar.autoconfigure.excel.ExcelConfiguration;
import io.github.bootystar.autoconfigure.jackson.JacksonConfiguration;
import io.github.bootystar.autoconfigure.mybatisplus.MybatisPlusConfiguration;
import io.github.bootystar.autoconfigure.properties.BootystarProperties;
import io.github.bootystar.autoconfigure.redis.RedisConfiguration;
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
        RedisConfiguration.class,
        ExcelConfiguration.class
})
public class BootystarAutoConfiguration {


}
