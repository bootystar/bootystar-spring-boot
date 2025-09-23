package io.github.bootystar.autoconfigure.properties.support;

import lombok.Data;

/**
 * AOP配置属性类
 * 用于配置AOP相关属性
 *
 * @author bootystar
 */
@Data
public class AopProperties {
    /**
     * 是否启用AOP功能
     */
    private boolean enabled = true;
}