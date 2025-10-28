package com.selimhorri.app.exception.wrapper;

public class OrderObjectNotFoundException extends RuntimeException {
    
    public OrderObjectNotFoundException(String message) {
        super(message);
    }
    
    public OrderObjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
