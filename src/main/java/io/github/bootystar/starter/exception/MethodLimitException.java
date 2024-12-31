package io.github.bootystar.starter.exception;

/**
 * @author bootystar
 */
public class MethodLimitException extends RuntimeException{

    public MethodLimitException(String message , Object... args) {
        super(String.format(message, args));
    }


}
