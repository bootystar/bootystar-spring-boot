package io.github.bootystar.starter.spring.handler;

import java.lang.reflect.Method;

/**
 * @author bootystar
 */
public interface MethodLimitHandler {

    /**
     * 获取参数签名
     *
     * @param method     方法
     * @param args       方法参数
     * @param expression spEL表达式
     * @return {@link String }
     * @author bootystar
     */
    String signature(Method method , Object[] args, String expression);

    /**
     * 上锁
     *
     * @param signature 签名
     * @return boolean 是否成功获取锁
     * @author bootystar
     */
    boolean tryLock(String signature);

    /**
     * 解锁
     *
     * @param signature 签名
     * @author bootystar
     */
    void unLock(String signature);

    /**
     * 超时回退的返回值
     *
     * @param signature 签名
     * @return {@link Object }
     * @author bootystar
     */
    Object fallback(String signature);
}
