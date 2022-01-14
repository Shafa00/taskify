package com.taskify.exception;

public class ExpiredOtpException extends RuntimeException{

    public ExpiredOtpException(String message) {
        super(message);
    }
}
