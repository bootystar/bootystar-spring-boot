package io.github.bootystar.starter.spring.aop.handler;

/**
 * @author bootystar
 */
public interface MethodLimitHandler {

    boolean tryLock(int timeout, String signature);

    void unLock(String signature);

}
