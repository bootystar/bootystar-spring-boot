package io.github.bootystar.starter.spring.annotation;

import io.github.bootystar.starter.spring.handler.MethodLimitHandler;

import java.lang.annotation.*;

/**
 * @author bootystar
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodLimit {

    /**
     * SpEL表达式(#参数名, #参数.属性)
     *
     * @return {@link String }
     * @author bootystar
     */
    String value() default "";

    /**
     * 限流处理器
     *
     * @return {@link Class }<{@link ? } {@link extends } {@link MethodLimitHandler }>
     * @author bootystar
     */
    Class<? extends MethodLimitHandler> handler() default MethodLimitHandler.class;

}
