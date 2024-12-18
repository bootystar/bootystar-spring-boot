package io.github.bootystar.starter.web.annotation;

import io.github.bootystar.starter.web.interfaces.LimitHandler;

import java.lang.annotation.*;

/**
 * @author bootystar
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodLimit {

    /**
     * 请求间隔的单位时间(毫秒)
     * <p>
     * 默认1秒
     */
    long time() default 1000L;

    /**
     * 提示信息
     * <p>
     * 触发限制时的提示信息
     */
    String message() default "处理中，请稍候再试";

    /**
     * 限流的处理程序
     *
     * @return {@link Class }<{@link ? } {@link extends } {@link LimitHandler }>
     * @author bootystar
     */
    Class<? extends LimitHandler> handler() default LimitHandler.class;

}
