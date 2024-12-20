package io.github.bootystar.starter.spring.aop.aspect;

import io.github.bootystar.starter.spring.aop.annotation.MethodLimit;
import io.github.bootystar.starter.spring.aop.exception.MethodLimitException;
import io.github.bootystar.starter.spring.aop.handler.MethodLimitHandler;
import io.github.bootystar.starter.spring.aop.handler.MethodSignatureHandler;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author bootystar
 */
@Aspect
public class MethodLimitAspect {
    private final Map<Class<?>, MethodSignatureHandler> SIGNATURE_HANDLER_MAP = new ConcurrentHashMap<>();
    private final Map<Class<?>, MethodLimitHandler> LIMIT_HANDLER_MAP = new ConcurrentHashMap<>();

    @Pointcut("@annotation(io.github.bootystar.starter.spring.aop.annotation.MethodLimit)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        MethodLimit annotation = method.getAnnotation(MethodLimit.class);
        int timeout = annotation.timeout();
        String message = annotation.message();
        MethodSignatureHandler signatureHandler = signatureHandler(annotation.signatureHandler());
        MethodLimitHandler limitHandler = limitHandler(annotation.limitHandler());
        String springExpression = annotation.value();
        String signature = signatureHandler.signature(method, joinPoint.getArgs(), springExpression);
        boolean b = limitHandler.tryLock(timeout, signature);
        try {
            if (!b) {
                throw new MethodLimitException(message);
            }
            return joinPoint.proceed();
        } finally {
            if (b) {
                limitHandler.unLock(signature);
            }
        }
    }

    @SneakyThrows
    private MethodSignatureHandler signatureHandler(Class<? extends MethodSignatureHandler> signatureHandler) {
        MethodSignatureHandler methodSignatureHandler = SIGNATURE_HANDLER_MAP.get(signatureHandler);
        if (methodSignatureHandler != null) {
            return methodSignatureHandler;
        }
        SIGNATURE_HANDLER_MAP.put(signatureHandler, signatureHandler.getConstructor().newInstance());
        return SIGNATURE_HANDLER_MAP.get(signatureHandler);
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

    public void setSignatureHandler(Class<? extends MethodSignatureHandler> clazz, MethodSignatureHandler methodSignatureHandler) {
        SIGNATURE_HANDLER_MAP.put(clazz, methodSignatureHandler);
    }

    public void setLimitHandler(Class<? extends MethodLimitHandler> clazz, MethodLimitHandler methodLimitHandler) {
        LIMIT_HANDLER_MAP.put(clazz, methodLimitHandler);
    }

}
