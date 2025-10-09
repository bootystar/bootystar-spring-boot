package io.github.bootystar.autoconfigure.aop.handler.impl;

import io.github.bootystar.autoconfigure.aop.annotation.MethodLimit;
import io.github.bootystar.autoconfigure.aop.handler.MethodLimitHandler;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RedissonClient;


/**
 * 基于 Redisson 的分布式方法限流处理器实现。
 * <p>
 * 此实现利用 Redisson 的 RRateLimiter 功能，适用于集群环境。
 *
 * @author bootystar
 */
public class RedissonMethodLimitHandler implements MethodLimitHandler {
    private final RedissonClient redissonClient;

    public RedissonMethodLimitHandler(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public boolean doLimit(String signature, MethodLimit methodLimit) {
        // 使用方法签名作为RateLimiter的键
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(signature);
        // todo 

        return !rateLimiter.tryAcquire();
    }
}
