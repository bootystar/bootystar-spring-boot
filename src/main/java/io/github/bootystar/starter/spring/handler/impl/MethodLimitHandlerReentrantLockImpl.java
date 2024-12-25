package io.github.bootystar.starter.spring.handler.impl;

import io.github.bootystar.starter.spring.handler.base.MethodLimitHandlerBase;
import lombok.SneakyThrows;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author bootystar
 */
public class MethodLimitHandlerReentrantLockImpl extends MethodLimitHandlerBase {
    private final ConcurrentHashMap<String, ReentrantLock> LOCK_MAP = new ConcurrentHashMap<>();
    private final ReentrantLock LOCK = new ReentrantLock();

    @Override
    @SneakyThrows
    public boolean tryLock(String signature) {
        LOCK.lock();
        try {
            return getLock(signature).tryLock();
        } finally {
            LOCK.unlock();
        }
    }

    @Override
    public void unLock(String signature) {
        LOCK.lock();
        try {
            ReentrantLock lock = getLock(signature);
            lock.unlock();
            if (!lock.isLocked()){
                LOCK_MAP.remove(signature);
            }
        } finally {
            LOCK.unlock();
        }
    }

    private ReentrantLock getLock(String signature) {
        ReentrantLock lock = LOCK_MAP.get(signature);
        if (lock != null) {
            return lock;
        }
        lock = new ReentrantLock();
        LOCK_MAP.putIfAbsent(signature, lock);
        return LOCK_MAP.get(signature);
    }


}
