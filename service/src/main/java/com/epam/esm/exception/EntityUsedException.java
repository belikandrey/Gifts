package com.epam.esm.exception;

public class EntityUsedException extends RuntimeException{
    public EntityUsedException() {
    }

    public EntityUsedException(String message) {
        super(message);
    }
}
