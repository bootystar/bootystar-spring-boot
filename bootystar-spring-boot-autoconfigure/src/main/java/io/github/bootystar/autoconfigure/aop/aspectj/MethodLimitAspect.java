package io.github.bootystar.autoconfigure.aop.aspectj;

import io.github.bootystar.autoconfigure.aop.annotation.MethodLimit;
import io.github.bootystar.autoconfigure.aop.exception.MethodLimitException;
import io.github.bootystar.autoconfigure.aop.handler.MethodLimitHandler;
import io.github.bootystar.autoconfigure.aop.handler.SignatureProvider;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * @author bootystar
 */
@Aspect
@RequiredArgsConstructor
public class MethodLimitAspect {
    private final SignatureProvider signatureHandler;
    private final MethodLimitHandler limitHandler;

    @Before("@annotation(methodLimit)")
    public void around(JoinPoint joinPoint, MethodLimit methodLimit) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        String springExpression = methodLimit.value();
        String signature = signatureHandler.signature(joinPoint.getTarget(), method, joinPoint.getArgs(), springExpression);
        boolean b = limitHandler.doLimit(signature,methodLimit);
        if (!b) {
            String errorMessage = String.format(
                    "Method call frequency has exceeded the limit: no more than %d requests within %d seconds are allowed.",
                    methodLimit.count(),
                    methodLimit.seconds()
            );
            throw new MethodLimitException(errorMessage, methodLimit.seconds(), methodLimit.count());
        }
    }

}