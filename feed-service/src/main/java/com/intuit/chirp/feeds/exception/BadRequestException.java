package com.intuit.chirp.feeds.exception;

public class BadRequestException extends RuntimeException{

    public BadRequestException(String message) {
        super(message);
    }
}