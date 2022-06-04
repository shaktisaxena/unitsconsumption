package de.shakti.stromverbrauch.exceptions;

public class CustomParameterConstraintException extends Exception {
    public CustomParameterConstraintException() {
        super();
    }

    public CustomParameterConstraintException(String message) {
        super(message);
    }

    public CustomParameterConstraintException(String message, Throwable cause) {
        super(message, cause);
    }

    protected CustomParameterConstraintException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
