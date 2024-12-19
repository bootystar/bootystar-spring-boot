package io.github.bootystar.starter.spring.aop.exception;

/**
 * @author bootystar
 */
public class MethodLimitException extends RuntimeException{

    public MethodLimitException(String message , Object... args) {
        super(String.format(message, args));
    }


}
