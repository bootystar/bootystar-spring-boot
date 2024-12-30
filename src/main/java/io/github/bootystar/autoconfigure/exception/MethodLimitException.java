package io.github.bootystar.autoconfigure.exception;

/**
 * @author bootystar
 */
public class MethodLimitException extends RuntimeException{

    public MethodLimitException(String message , Object... args) {
        super(String.format(message, args));
    }


}
