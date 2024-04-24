package com.hospitalproject.exceptions;

public class TitleNotFoundException extends RuntimeException {
    public TitleNotFoundException(String message) {
        super(message);
    }
}
