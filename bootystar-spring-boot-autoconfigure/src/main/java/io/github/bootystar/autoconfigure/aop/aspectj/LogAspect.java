package io.github.bootystar.autoconfigure.aop.aspectj;

import io.github.bootystar.autoconfigure.aop.annotation.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;


/**
 *
 * @author bootystar
 */
@Aspect
public class LogAspect {

    @Around("@annotation(log)")
    public Object around(ProceedingJoinPoint joinPoint, Log log) throws Throwable {

        Object proceed = joinPoint.proceed();
        return proceed;
    }

}
