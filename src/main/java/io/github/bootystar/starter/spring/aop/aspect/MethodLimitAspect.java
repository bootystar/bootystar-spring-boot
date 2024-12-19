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
    private final Map<Class<?>, MethodSignatureHandler> map = new ConcurrentHashMap<>();

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
        try {
            boolean b = limitHandler.tryLock(timeout, signature);
            if (!b) {
                throw new MethodLimitException(message);
            }
            return joinPoint.proceed();
        } finally {
            limitHandler.unLock(signature);
        }
    }

    @SneakyThrows
    private MethodSignatureHandler signatureHandler(Class<? extends MethodSignatureHandler> signatureHandler) {
        return signatureHandler.newInstance();
    }

    @SneakyThrows
    private MethodLimitHandler limitHandler(Class<? extends MethodLimitHandler> signatureHandler) {
        return signatureHandler.newInstance();
    }

}
