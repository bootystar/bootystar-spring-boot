package io.github.bootystar.autoconfigure.aop.handler;

/**
 * 方法限流处理器接口。
 * <p>
 * 定义了尝试获取和释放锁的契约。
 *
 * @author bootystar
 */
public interface MethodLimitHandler {

    /**
     * 尝试获取锁。
     *
     * @param signature 锁的唯一签名
     * @return 如果成功获取锁，则返回 {@code true}；否则返回 {@code false}
     */
    boolean tryLock(String signature);

    /**
     * 释放锁。
     *
     * @param signature 锁的唯一签名
     */
    void unLock(String signature);

}
