package io.github.bootystar.autoconfigure.aop.annotation;

import io.github.bootystar.autoconfigure.aop.exception.MethodLimitException;
import io.github.bootystar.autoconfigure.aop.handler.MethodLimitHandler;

import java.lang.annotation.*;

/**
 * 为方法启用访问限制（方法锁）。
 * <p>
 * 当限流被触发时，将会抛出一个 {@link MethodLimitException}。
 * 此注解需要一个已配置的 {@link MethodLimitHandler} 实例来协同工作。
 *
 * @author bootystar
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodLimit {

    /**
     * 用于计算锁签名的 SpEL (Spring表达式语言) 表达式。
     * <p>
     * 表达式的计算上下文为方法的参数。例如: {@code "#userId"} 或 {@code "#request.getRemoteAddr()"}。
     * 如果为空，将根据方法签名和所有参数生成一个默认的签名。
     *
     * @return SpEL 表达式
     */
    String value() default "";

    /**
     * 当访问限制被触发时，抛出的异常中所包含的消息。
     *
     * @return 异常消息
     */
    String message() default "处理中，请稍后重试";

}
