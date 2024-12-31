package io.github.bootystar.autoconfigure.prop;

import io.github.bootystar.autoconfigure.prop.support.*;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author bootystar
 */
@Data
@ConfigurationProperties(prefix = "bootystar")
public class BootystarProperties {
    private String timeFormat = "HH:mm:ss";
    private String dateFormat = "yyyy-MM-dd";
    private String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
    private String timeZone = "GMT+8";
    private AopProperties aop = new AopProperties();
    private ConverterProperties converter = new ConverterProperties();
    private JacksonProperties jackson = new JacksonProperties();
    private MybatisPlusProperties mybatisPlus = new MybatisPlusProperties();
    private RedisProperties redis = new RedisProperties();
}
