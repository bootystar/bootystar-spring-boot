package io.github.bootystar.autoconfigure.aop.aspectj;

import io.github.bootystar.autoconfigure.aop.annotation.MethodLimit;
import io.github.bootystar.autoconfigure.aop.exception.MethodLimitException;
import io.github.bootystar.autoconfigure.aop.handler.MethodLimitHandler;
import io.github.bootystar.autoconfigure.aop.handler.MethodSignatureHandler;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * @author bootystar
 */
@Aspect
@RequiredArgsConstructor
public class MethodLimitAspect {
    private final MethodSignatureHandler signatureHandler;
    private final MethodLimitHandler limitHandler;

    @Around("@annotation(methodLimit)")
    public Object around(ProceedingJoinPoint joinPoint, MethodLimit methodLimit) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        String springExpression = methodLimit.value();
        String signature = signatureHandler.signature(joinPoint.getTarget(), method, joinPoint.getArgs(), springExpression);
        boolean b = limitHandler.tryLock(signature);
        try {
            if (!b) {
                throw new MethodLimitException(methodLimit.message());
            }
            return joinPoint.proceed();
        } finally {
            if (b) {
                limitHandler.unLock(signature);
            }
        }
    }

}