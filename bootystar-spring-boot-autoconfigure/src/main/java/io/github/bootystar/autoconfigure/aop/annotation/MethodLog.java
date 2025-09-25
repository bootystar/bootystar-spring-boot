package io.github.bootystar.autoconfigure.aop.annotation;

import java.lang.annotation.*;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodLog {

    /**
     * 是否打印日志
     */
    boolean value() default true;

}