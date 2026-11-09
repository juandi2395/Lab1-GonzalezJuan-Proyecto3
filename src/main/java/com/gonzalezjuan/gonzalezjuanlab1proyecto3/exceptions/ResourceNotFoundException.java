package com.gonzalezjuan.gonzalezjuanlab1proyecto3.exceptions;

public class ResourceNotFoundException
        extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}