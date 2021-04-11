package com.epam.esm.exception;


/**
 * Entity already exist exception. Throws if object already exists in database
 *
 * @author Andrey Belik
 * @version 1.0
 */
public class EntityAlreadyExistException extends RuntimeException {
    /**
     * Constructor
     *
     * @param message message of the exception
     */
    public EntityAlreadyExistException(String message) {
        super(message);
    }
    /**
     * Constructor
     *
     * @param cause another exception
     */
    public EntityAlreadyExistException( Throwable cause) {
        super(cause);
    }
}
