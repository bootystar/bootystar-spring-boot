package io.github.bootystar.autoconfigure.aop.annotation;

import io.github.bootystar.autoconfigure.aop.exception.MethodLimitException;
import io.github.bootystar.autoconfigure.aop.handler.MethodLimitHandler;

import java.lang.annotation.*;

/**
 * 注解限流
 * <p>
 * 触发限流时抛出{@link MethodLimitException}
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
     */
    String value() default "";

    /**
     * 触发限流时抛出的异常提示信息
     *
     * @return {@link String }
     */
    String message() default "processing, please try again later";

}
