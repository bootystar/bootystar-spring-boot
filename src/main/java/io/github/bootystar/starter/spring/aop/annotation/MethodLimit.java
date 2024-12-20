package io.github.bootystar.starter.spring.aop.annotation;

import io.github.bootystar.starter.spring.aop.handler.MethodLimitHandler;
import io.github.bootystar.starter.spring.aop.handler.MethodSignatureHandler;
import io.github.bootystar.starter.spring.aop.handler.impl.MethodLimitHandlerImpl;
import io.github.bootystar.starter.spring.aop.handler.impl.MethodSignatureHandlerImpl;

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
     * 超时时间(毫秒)
     * <p>
     * 当超过该时间未获取到锁时,抛出异常
     */
    int timeout() default 0;

    /**
     * 提示信息
     * <p>
     * 触发限制时的提示信息
     */
    String message() default "processing, please try again later";

    /**
     * 签名处理器
     *
     * @return {@link Class }<{@link ? } {@link extends } {@link MethodSignatureHandler }>
     * @author bootystar
     */
    Class<? extends MethodSignatureHandler> signatureHandler() default MethodSignatureHandler.class;

    /**
     * 限流处理器
     *
     * @return {@link Class }<{@link ? } {@link extends } {@link MethodLimitHandler }>
     * @author bootystar
     */
    Class<? extends MethodLimitHandler> limitHandler() default MethodLimitHandler.class;

}
