package io.github.bootystar.autoconfigure.aop.annotation;

import java.lang.annotation.*;

/**
 * 日志记录
 * @author bootystar
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    
}
