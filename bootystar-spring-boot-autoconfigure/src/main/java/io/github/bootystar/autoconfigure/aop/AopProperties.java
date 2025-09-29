package io.github.bootystar.autoconfigure.aop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * AOP（面向切面编程）相关配置属性
 *
 * @author bootystar
 */
@ConfigurationProperties("bootystar.aop")
@Data
public class AopProperties {
    /**
     * 是否启用AOP自动配置。默认为 true。
     */
    private boolean enabled = true;
    /**
     * 方法限流锁的键名前缀。默认为 "method_limit:"。
     */
    private String methodLimitPrefix = "method_limit:";
}
