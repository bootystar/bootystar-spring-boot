package io.github.bootystar.starter.spring.aop.handler;

import java.lang.reflect.Method;

/**
 * @author bootystar
 */
public interface MethodSignatureHandler {

    String signature(Method method , Object[] args, String expression);

}
