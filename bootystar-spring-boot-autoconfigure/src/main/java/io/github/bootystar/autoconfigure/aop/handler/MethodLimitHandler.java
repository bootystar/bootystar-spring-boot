package io.github.bootystar.autoconfigure.aop.handler;

import io.github.bootystar.autoconfigure.aop.annotation.MethodLimit;

/**
 * 方法限流处理器接口。
 * <p>
 * 定义了尝试获取和释放锁的契约。
 *
 * @author bootystar
 */
public interface MethodLimitHandler {

    /**
     * 是否限流
     *
     * @param signature 唯一签名
     * @param methodLimit 方法限流注解
     * @return 是否限流
     */
    boolean doLimit(String signature, MethodLimit methodLimit);

}
