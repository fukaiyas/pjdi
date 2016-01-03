package com.bugworm.pjdi;

/**
 * An exception class for PJDI project.
 */
public class PjdiException extends RuntimeException{

    /**
     * Constructs a PjdiException with the specified detail message.
     * @param message The detail message
     */
    public PjdiException(String message){
        super(message);
    }

    /**
     * Constructs a PjdiException with the specified detail message and cause.
     * @param message The detail message
     * @param throwable The cause
     */
    public PjdiException(String message, Throwable throwable){
        super(message, throwable);
    }
}
