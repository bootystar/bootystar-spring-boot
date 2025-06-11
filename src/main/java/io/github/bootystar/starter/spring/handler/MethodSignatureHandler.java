package io.github.bootystar.starter.spring.handler;

import java.lang.reflect.Method;

/**
 * @author bootystar
 */
public interface MethodSignatureHandler {

    /**
     * 获取参数签名
     *
     * @param target     目标对象
     * @param method     方法
     * @param args       方法参数
     * @param expression 表达式
     * @return {@link String }
     */
    String signature(Object target, Method method, Object[] args, String expression);

}
