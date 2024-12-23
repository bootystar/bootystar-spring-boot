package io.github.bootystar.starter.spring.aop.aspect;

import io.github.bootystar.starter.spring.annotation.MethodLimit;
import io.github.bootystar.starter.spring.aop.handler.MethodLimitHandler;
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
    private final Map<Class<?>, MethodLimitHandler> LIMIT_HANDLER_MAP = new ConcurrentHashMap<>();

    @Pointcut("@annotation(io.github.bootystar.starter.spring.annotation.MethodLimit)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        MethodLimit annotation = method.getAnnotation(MethodLimit.class);
        MethodLimitHandler limitHandler = limitHandler(annotation.handler());
        String springExpression = annotation.value();
        String signature = limitHandler.signature(method, joinPoint.getArgs(), springExpression);
        boolean b = limitHandler.tryLock(signature);
        try {
            if (!b) {
                return limitHandler.fallback(signature);
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
