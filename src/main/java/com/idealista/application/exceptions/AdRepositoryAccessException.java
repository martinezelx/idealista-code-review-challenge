package com.idealista.application.exceptions;

public class AdRepositoryAccessException extends RuntimeException {
    public AdRepositoryAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
