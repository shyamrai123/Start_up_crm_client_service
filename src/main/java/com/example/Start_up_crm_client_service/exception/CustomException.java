package com.example.Start_up_crm_client_service.exception;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
public class CustomException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(CustomException.class);
    private final HttpStatus status;
    private final String errorCode;

    public CustomException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
        this.errorCode = "BAD_REQUEST";
        logError(message);
    }

    public CustomException(String message, HttpStatus status, String errorCode) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
        logError(message);
    }

    public CustomException(String message, Exception e) {
        super(message, e);
        this.status = HttpStatus.BAD_REQUEST;
        this.errorCode = "BAD_REQUEST";
        logError(message);
    }

    private void logError(String message) {
        logger.error("Custom Exception: {}", message);
    }
}
