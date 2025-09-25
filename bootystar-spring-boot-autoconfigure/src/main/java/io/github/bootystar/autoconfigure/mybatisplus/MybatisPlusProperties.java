package io.github.bootystar.autoconfigure.mybatisplus;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * mybatis plus属性
 *
 * @author bootystar
 */
@ConfigurationProperties("bootystar.mybatis-plus")
@Data
public class MybatisPlusProperties {
    /**
     * 是否添加乐观锁拦截器
     */
    private boolean optimisticLocker =  true;
    /**
     * 是否添加分页拦截器
     */
    private boolean pagination = true;
    /**
     * 是否添加防全表更新拦截器
     */
    private boolean blockAttack =  true;
}
