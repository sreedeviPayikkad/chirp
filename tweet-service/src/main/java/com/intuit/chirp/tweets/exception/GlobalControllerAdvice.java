package com.intuit.chirp.tweets.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;

@ControllerAdvice
@Slf4j
public class GlobalControllerAdvice {
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse<Violation> onMethodConstraintViolationException(ConstraintViolationException e) {
        ErrorResponse<Violation> error = new ErrorResponse<>(new ArrayList<>());
        error.errors().add(new Violation("constraint error", e.getMessage()));
        log.error("constraint violation error", e);
        return error;
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse<Violation> onMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        ErrorResponse<Violation> error = new ErrorResponse<>(new ArrayList<>());
        error.errors().add(new Violation(e.getParameter().getParameterName(), e.getMessage()));
        log.error("method argument mismatch", e);
        return error;
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    ErrorResponse<ImmutablePair<String, String>> onRuntimeException(RuntimeException e) {
        ErrorResponse<ImmutablePair<String, String>> error = new ErrorResponse<>(new ArrayList<>());
        error.errors().add(new ImmutablePair<>("message", e.getMessage()));
        log.error("runtime exception", e);
        return error;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse<Violation> onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ErrorResponse<Violation> error = new ErrorResponse<>(new ArrayList<>());
        error.errors().add(new Violation(e.getParameter().getParameterName(), e.getMessage()));
        log.error("method argument mismatch : ", e);
        return error;
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    ErrorResponse<ImmutablePair<String, String>> onUserNotFoundException(UserNotFoundException e) {
        ErrorResponse<ImmutablePair<String, String>> error = new ErrorResponse<>(new ArrayList<>());
        error.errors().add(new ImmutablePair<>("message", e.getMessage()));
        log.error("user not found", e);
        return error;
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse<ImmutablePair<String, String>> onBadRequestException(BadRequestException e) {
        ErrorResponse<ImmutablePair<String, String>> error = new ErrorResponse<>(new ArrayList<>());
        error.errors().add(new ImmutablePair<>("message", e.getMessage()));
        log.error("bad request", e);
        return error;
    }
}
