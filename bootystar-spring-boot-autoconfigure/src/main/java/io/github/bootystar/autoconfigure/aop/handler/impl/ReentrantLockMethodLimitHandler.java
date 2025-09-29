package io.github.bootystar.autoconfigure.aop.handler.impl;

import io.github.bootystar.autoconfigure.aop.handler.MethodLimitHandler;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 基于 {@link ReentrantLock} 的本地内存方法限流处理器实现。
 * <p>
 * 此实现适用于单实例应用程序，不适用于集群环境。
 * <p>
 * 注意：此处理器不会从内部 Map 中移除锁对象，以避免复杂的并发问题和潜在的内存泄漏。
 * Map 的大小将增长到与唯一被锁定方法签名数量相同。
 *
 * @author bootystar
 */
public class ReentrantLockMethodLimitHandler implements MethodLimitHandler {

    // 使用接口类型和更具描述性的名称
    private final ConcurrentMap<String, ReentrantLock> signatureLocks = new ConcurrentHashMap<>();

    @Override
    public boolean tryLock(String signature) {
        // 使用 computeIfAbsent 实现原子性、高性能的获取或创建操作。
        // 这是实现此模式的标准且正确的方法。
        ReentrantLock lock = signatureLocks.computeIfAbsent(signature, k -> new ReentrantLock());
        return lock.tryLock();
    }

    @Override
    public void unLock(String signature) {
        ReentrantLock lock = signatureLocks.get(signature);
        // 仅当锁存在且由当前线程持有（可重入）时才尝试解锁。
        // 这可以防止 IllegalMonitorStateException。
        if (lock != null && lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
        // 注意：我们不从 Map 中移除锁，以防止竞态条件和复杂生命周期管理导致的内存泄漏。
        // Map 的大小将增长到与唯一被锁定方法签名数量相同。
    }
}
