package com.project.plantOne.exception;

public class GlobalException extends RuntimeException{
    private String message;
    private Throwable cause;
    private boolean enableSuppression;
    private boolean writableStackTrac;
    public GlobalException(String message,boolean enableSuppression) {
        super(message, null,false,enableSuppression);
        this.message = message;
        this.enableSuppression = enableSuppression;
    }
    public GlobalException() {
    }
}
