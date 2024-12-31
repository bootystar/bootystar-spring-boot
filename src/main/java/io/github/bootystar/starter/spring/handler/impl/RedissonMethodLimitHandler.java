package io.github.bootystar.starter.spring.handler.impl;

import io.github.bootystar.starter.spring.handler.MethodLimitHandler;
import lombok.SneakyThrows;
import org.redisson.api.RedissonClient;

import java.util.concurrent.locks.Lock;

/**
 * @author bootystar
 */
public class RedissonMethodLimitHandler implements MethodLimitHandler {
    private final RedissonClient redissonClient;

    public RedissonMethodLimitHandler(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    @SneakyThrows
    public boolean tryLock(String signature) {
        return getLock(signature).tryLock();
    }

    @Override
    public void unLock(String signature) {
        getLock(signature).unlock();
    }

    private Lock getLock(String signature) {
        return redissonClient.getLock(signature);
    }


}
