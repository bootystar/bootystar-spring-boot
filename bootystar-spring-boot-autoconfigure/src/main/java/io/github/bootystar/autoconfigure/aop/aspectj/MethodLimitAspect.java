package io.github.bootystar.autoconfigure.aop.aspectj;

import io.github.bootystar.autoconfigure.aop.annotation.MethodLimit;
import io.github.bootystar.autoconfigure.aop.exception.MethodLimitException;
import io.github.bootystar.autoconfigure.aop.handler.MethodLimitHandler;
import io.github.bootystar.autoconfigure.aop.handler.MethodSignatureHandler;
import io.github.bootystar.autoconfigure.aop.handler.impl.SpelMethodSignatureHandler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author bootystar
 */
@Aspect
public class MethodLimitAspect {
    private final Map<Class<? extends MethodLimitHandler>, MethodLimitHandler> limitHandlerMap = new ConcurrentHashMap<>();
    private final MethodSignatureHandler signatureHandler = new SpelMethodSignatureHandler();

    @Around("@annotation(methodLimit)")
    public Object around(ProceedingJoinPoint joinPoint, MethodLimit methodLimit) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        String springExpression = methodLimit.value();
        String signature = signatureHandler.signature(joinPoint.getTarget(), method, joinPoint.getArgs(), springExpression);
        MethodLimitHandler limitHandler = getLimitHandler(methodLimit.handler());

        boolean locked = limitHandler.tryLock(signature);
        if (!locked) {
            throw new MethodLimitException(methodLimit.message());
        }

        try {
            return joinPoint.proceed();
        } finally {
            limitHandler.unLock(signature);
        }
    }

    private MethodLimitHandler getLimitHandler(Class<? extends MethodLimitHandler> handlerClass) {
        return limitHandlerMap.computeIfAbsent(handlerClass, clazz -> {
            try {
                return clazz.getConstructor().newInstance();
            } catch (ReflectiveOperationException e) {
                throw new IllegalArgumentException("Cannot instantiate MethodLimitHandler: " + clazz.getName(), e);
            }
        });
    }

    public void allocateLimitHandler(Class<? extends MethodLimitHandler> clazz, MethodLimitHandler methodLimitHandler) {
        limitHandlerMap.put(clazz, methodLimitHandler);
    }
}