package io.github.bootystar.starter.spring.handler;

/**
 * @author bootystar
 */
public interface MethodLimitHandler {

    /**
     * 上锁
     *
     * @param signature 签名
     * @return boolean 是否成功获取锁
     * @author bootystar
     */
    boolean tryLock(String signature);

    /**
     * 解锁
     *
     * @param signature 签名
     * @author bootystar
     */
    void unLock(String signature);

}
