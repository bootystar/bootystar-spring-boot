package io.github.bootystar.autoconfigure.aop.handler.impl;

import io.github.bootystar.autoconfigure.aop.handler.MethodLimitHandler;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author bootystar
 */
public class ReentrantLockMethodLimitHandler implements MethodLimitHandler {
    private final ConcurrentHashMap<String, ReentrantLock> lockMap = new ConcurrentHashMap<>();

    @Override
    public boolean tryLock(String signature) {
        return getLock(signature).tryLock();
    }

    @Override
    public void unLock(String signature) {
        ReentrantLock lock = lockMap.get(signature);
        if (lock != null && lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }

    private ReentrantLock getLock(String signature) {
        return lockMap.computeIfAbsent(signature, k -> new ReentrantLock());
    }
}