package io.github.bootystar.starter.spring.annotation;

import io.github.bootystar.starter.spring.handler.MethodLimitHandler;

import java.lang.annotation.*;

/**
 * 注解限流
 * <p>
 * 触发限流时抛出{@link io.github.bootystar.starter.exception.MethodLimitException}
 * @author bootystar
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodLimit {

    /**
     * 计算签名的表达式
     * 默认使用SpEL表达式(#参数名, #参数.属性)
     *
     * @return {@link String }
     * @author bootystar
     */
    String value() default "";

    /**
     * 触发限流时抛出的异常提示信息
     *
     * @return {@link String }
     * @author bootystar
     */
    String message() default "processing, please try again later";

    /**
     * 限流处理器
     *
     * @return {@link Class }<{@link ? } {@link extends } {@link MethodLimitHandler }>
     * @author bootystar
     */
    Class<? extends MethodLimitHandler> limitHandler() default MethodLimitHandler.class;

}
