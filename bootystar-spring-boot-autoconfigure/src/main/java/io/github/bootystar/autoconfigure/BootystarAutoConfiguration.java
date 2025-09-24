package io.github.bootystar.autoconfigure;

import io.github.bootystar.autoconfigure.aop.AopAutoConfiguration;
import io.github.bootystar.autoconfigure.converter.ConverterAutoConfiguration;
import io.github.bootystar.autoconfigure.excel.ExcelAutoConfiguration;
import io.github.bootystar.autoconfigure.jackson.JacksonAutoConfiguration;
import io.github.bootystar.autoconfigure.mybatisplus.MybatisPlusAutoConfiguration;
import io.github.bootystar.autoconfigure.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * 自动配置类
 * @author bootystar
 */
@AutoConfiguration
@Import({
        AopAutoConfiguration.class,
        ConverterAutoConfiguration.class,
        JacksonAutoConfiguration.class,
        MybatisPlusAutoConfiguration.class,
        RedisAutoConfiguration.class,
        ExcelAutoConfiguration.class
})
public class BootystarAutoConfiguration {


}
