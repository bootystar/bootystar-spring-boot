package io.github.bootystar.autoconfigure.aop.spi;

import io.github.bootystar.autoconfigure.aop.annotation.RateLimit;

/**
 * 方法限流器。
 *
 * @author bootystar
 */
public interface RateLimiter {

    /**
     * 执行限流
     *
     * @param signature 唯一签名
     * @param rateLimit 方法限流注解
     * @return 是否限流
     */
    boolean doLimit(String signature, RateLimit rateLimit);

}
