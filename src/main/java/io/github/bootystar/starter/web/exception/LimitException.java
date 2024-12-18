package io.github.bootystar.starter.web.exception;

/**
 * @author bootystar
 */
public class LimitException extends RuntimeException{

    public LimitException() {
        super("request out of limit");
    }

    public LimitException(String message , Object... args) {
        super(String.format(message, args));
    }


}
