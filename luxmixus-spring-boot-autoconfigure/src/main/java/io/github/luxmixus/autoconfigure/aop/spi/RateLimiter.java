package io.github.luxmixus.autoconfigure.aop.spi;

import io.github.luxmixus.autoconfigure.aop.annotation.RateLimit;

/**
 * 方法限流器。
 *
 * @author luxmixus
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
