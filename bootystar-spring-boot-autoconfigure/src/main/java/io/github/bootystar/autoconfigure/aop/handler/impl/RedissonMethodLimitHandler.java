package io.github.bootystar.autoconfigure.aop.handler.impl;

import io.github.bootystar.autoconfigure.aop.handler.MethodLimitHandler;
import org.redisson.api.RedissonClient;

import java.util.concurrent.locks.Lock;

/**
 * 基于 Redisson 的分布式方法限流处理器实现。
 * <p>
 * 此实现利用 Redisson 的分布式锁功能，适用于集群环境。
 *
 * @author bootystar
 */
public class RedissonMethodLimitHandler implements MethodLimitHandler {
    private final RedissonClient redissonClient;

    public RedissonMethodLimitHandler(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    /**
     * 尝试获取分布式锁。
     * <p>
     * {@link java.util.concurrent.locks.Lock#tryLock()} 不会抛出受检异常，
     * 因此无需使用 {@code @SneakyThrows}。
     *
     * @param signature 锁的唯一签名
     * @return 如果成功获取锁，则返回 {@code true}；否则返回 {@code false}
     */
    @Override
    public boolean tryLock(String signature) {
        return getLock(signature).tryLock();
    }

    /**
     * 释放分布式锁。
     *
     * @param signature 锁的唯一签名
     */
    @Override
    public void unLock(String signature) {
        getLock(signature).unlock();
    }

    /**
     * 从 Redisson 客户端获取一个锁实例。
     *
     * @param signature 锁的唯一签名
     * @return {@link Lock} 实例
     */
    private Lock getLock(String signature) {
        return redissonClient.getLock(signature);
    }
}
