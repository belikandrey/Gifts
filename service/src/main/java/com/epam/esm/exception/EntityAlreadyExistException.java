package com.epam.esm.exception;

public class EntityAlreadyExistException extends RuntimeException {
    public EntityAlreadyExistException() {
    }

    public EntityAlreadyExistException(String message) {
        super(message);
    }

    public EntityAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
