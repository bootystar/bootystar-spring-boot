package io.github.bootystar.autoconfigure;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * “基于列表条件” 属性
 *
 * @author bootystar
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnListPropertyCondition.class)
public @interface ConditionalOnListProperty {
    
    String value();
    
    boolean matchIfEmpty() default false;
}