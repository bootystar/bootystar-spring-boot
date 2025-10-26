package io.github.luminsyn.autoconfigure.aop.spi;

import org.aspectj.lang.reflect.MethodSignature;

/**
 *
 * @author luminsyn
 */
public interface LogWriter {

    void before(Object target, MethodSignature signature, Object[] args);

    void after(Object target, MethodSignature signature, Object[] args, Object result, long duration);

    void error(Object target, MethodSignature signature, Object[] args, Throwable throwable, long duration);
}
