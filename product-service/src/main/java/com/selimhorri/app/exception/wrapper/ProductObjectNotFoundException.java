package com.selimhorri.app.exception.wrapper;

public class ProductObjectNotFoundException extends RuntimeException {
    
    public ProductObjectNotFoundException(String message) {
        super(message);
    }
    
    public ProductObjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
