package io.github.bootystar.starter.spring.aop.handler.impl;

import io.github.bootystar.starter.spring.aop.handler.MethodLimitHandler;
import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author bootystar
 */
public class MethodLimitHandlerImpl implements MethodLimitHandler {


    @Override
    @SneakyThrows
    public boolean tryLock(int timeout, String signature) {
        Lock lock = getLock(signature);
        if (timeout <= 0) {
            return lock.tryLock();
        }
        return lock.tryLock(timeout, TimeUnit.MILLISECONDS);
    }

    @Override
    public void unLock(String signature) {
        getLock(signature).unlock();
    }


    private Lock getLock(String signature) {
        ReentrantLock lock = new ReentrantLock();
        return lock;
    }


}
