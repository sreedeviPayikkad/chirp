package com.intuit.chirp.tweets.exception;

public class BadRequestException extends RuntimeException{

    public BadRequestException(String message) {
        super(message);
    }
}
