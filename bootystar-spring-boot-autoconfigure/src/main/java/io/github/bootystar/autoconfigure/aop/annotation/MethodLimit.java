package io.github.bootystar.autoconfigure.aop.annotation;


import java.lang.annotation.*;

/**
 * 方法限流
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
     * 限流周期,默认单位秒
     */
    int seconds() default 1;

    /**
     * 周期内的访问次数
     */
    int count() default 100;

}
