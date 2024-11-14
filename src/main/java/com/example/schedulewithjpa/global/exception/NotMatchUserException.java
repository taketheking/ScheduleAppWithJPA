package com.example.schedulewithjpa.global.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotMatchUserException extends RuntimeException {
    public NotMatchUserException(String message) {
        super(message);
    }
}
