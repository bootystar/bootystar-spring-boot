package io.github.bootystar.autoconfigure.mybatisplus;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
ser * MyBatis Plus properties
 *
 * @author bootystar
 */
@Data
@ConfigurationProperties("bootystar.mybatis-plus")
public class MybatisPlusProperties {
    /**
     * Whether to enable optimistic locker interceptor
     */
    private boolean optimisticLocker =  true;
    /**
     * Whether to enable pagination interceptor
     */
    private boolean pagination = true;
    /**
     * Whether to enable block attack interceptor
     */
    private boolean blockAttack =  true;
}