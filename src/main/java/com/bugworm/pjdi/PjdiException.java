package com.bugworm.pjdi;

public class PjdiException extends RuntimeException{
    public PjdiException(String message){
        super(message);
    }
    public PjdiException(String message, Throwable throwable){
        super(message, throwable);
    }
}
