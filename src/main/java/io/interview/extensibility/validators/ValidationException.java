package io.interview.extensibility.validators;

/**
 * Created by user on 2015-02-01.
 */
public class ValidationException extends Exception {

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }
}
