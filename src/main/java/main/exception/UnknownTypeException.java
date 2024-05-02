package main.exception;

public class UnknownTypeException extends RuntimeException {
    public UnknownTypeException(String message) {
        super(message);
    }
}
