package com.cmms.logistics.exception_handler;

public class RoleAuthorizationException extends RuntimeException{
    public RoleAuthorizationException(String message) {
        super(message);
    }
}
