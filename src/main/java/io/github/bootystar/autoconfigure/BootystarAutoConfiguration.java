package io.github.bootystar.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * bootystar自动配置
 *
 * @see org.springframework.context.annotation.ConditionEvaluator#shouldSkip(AnnotatedTypeMetadata) 条件注解判断源码
 * @author bootystar
 */
@AutoConfiguration
@EnableConfigurationProperties(BootystarProperties.class)
public class BootystarAutoConfiguration {

}
