package io.github.luxmixus.autoconfigure.aop.annotation;

import java.lang.annotation.*;

/**
 * 日志记录
 * @author luxmixus
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    
}
