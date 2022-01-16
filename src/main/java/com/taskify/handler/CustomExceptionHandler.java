package com.taskify.handler;

import com.taskify.exception.*;
import com.taskify.model.ResponseModel;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

@Log4j2
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception exception) {
        if (exception instanceof DataNotFoundException)
            return handleException(exception, HttpStatus.NOT_FOUND);

        else if (exception instanceof DuplicateUserException
                || exception instanceof ExpiredOtpException
                || exception instanceof InvalidModelException
                || exception instanceof InvalidOtpException
                || exception instanceof HttpMessageNotReadableException
                || exception instanceof MissingServletRequestParameterException
                || exception instanceof HttpRequestMethodNotSupportedException
                || exception instanceof MalformedJwtException
                || exception instanceof SignatureException
                || exception instanceof ExpiredJwtException
                || exception instanceof UnsupportedJwtException
                || exception instanceof MethodArgumentNotValidException
                || exception instanceof IllegalArgumentException
                || exception instanceof ConstraintViolationException)
            return handleException(exception, HttpStatus.BAD_REQUEST);

        throw new RuntimeException(String.valueOf(exception.getClass()));
    }

    private ResponseEntity<?> handleException(Exception exception, HttpStatus status) {
        log.error(exception.getMessage());
        return ResponseEntity.status(status)
                .body(ResponseModel.builder()
                        .status(status)
                        .body(exception.getMessage())
                        .build());
    }
}
