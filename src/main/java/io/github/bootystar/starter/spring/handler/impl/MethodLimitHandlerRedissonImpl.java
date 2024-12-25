package io.github.bootystar.starter.spring.handler.impl;

import io.github.bootystar.starter.spring.handler.base.MethodLimitHandlerBase;
import lombok.SneakyThrows;
import org.redisson.api.RedissonClient;

import java.util.concurrent.locks.Lock;

/**
 * @author bootystar
 */
public class MethodLimitHandlerRedissonImpl extends MethodLimitHandlerBase {
    private final RedissonClient redissonClient;
    public MethodLimitHandlerRedissonImpl(RedissonClient redissonClient) {
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
