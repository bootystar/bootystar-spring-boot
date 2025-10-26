package io.github.luminsyn.autoconfigure.aop.annotation;

import java.lang.annotation.*;

/**
 * 日志记录
 * @author luminsyn
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    
}
