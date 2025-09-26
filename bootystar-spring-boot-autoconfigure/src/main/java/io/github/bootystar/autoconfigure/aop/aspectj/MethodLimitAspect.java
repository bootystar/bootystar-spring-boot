package io.github.bootystar.autoconfigure.aop.aspectj;

import io.github.bootystar.autoconfigure.aop.annotation.MethodLimit;
import io.github.bootystar.autoconfigure.aop.exception.MethodLimitException;
import io.github.bootystar.autoconfigure.aop.handler.MethodLimitHandler;
import io.github.bootystar.autoconfigure.aop.handler.MethodSignatureHandler;
import io.github.bootystar.autoconfigure.aop.handler.impl.SpelMethodSignatureHandler;
import lombok.SneakyThrows;
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
    private final Map<Class<?>, MethodLimitHandler> LIMIT_HANDLER_MAP = new ConcurrentHashMap<>();
    private final MethodSignatureHandler signatureHandler = new SpelMethodSignatureHandler();

    @Around("@annotation(methodLimit)")
    public Object around(ProceedingJoinPoint joinPoint, MethodLimit methodLimit) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        String springExpression = methodLimit.value();
        String signature = signatureHandler.signature(joinPoint.getTarget(), method, joinPoint.getArgs(), springExpression);
        MethodLimitHandler limitHandler = limitHandler(methodLimit.handler());
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

    @SneakyThrows
    private MethodLimitHandler limitHandler(Class<? extends MethodLimitHandler> signatureHandler) {
        MethodLimitHandler methodSignatureHandler = LIMIT_HANDLER_MAP.get(signatureHandler);
        if (methodSignatureHandler != null) {
            return methodSignatureHandler;
        }
        LIMIT_HANDLER_MAP.put(signatureHandler, signatureHandler.getConstructor().newInstance());
        return LIMIT_HANDLER_MAP.get(signatureHandler);
    }

    public void allocateLimitHandler(Class<? extends MethodLimitHandler> clazz, MethodLimitHandler methodLimitHandler) {
        LIMIT_HANDLER_MAP.put(clazz, methodLimitHandler);
    }

}
