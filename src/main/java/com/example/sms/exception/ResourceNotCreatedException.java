package com.example.sms.exception;

public class ResourceNotCreatedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ResourceNotCreatedException(String msg) {
        super(msg);
    }
}
