package com.example.Start_up_crm_client_service.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JwtTokenParseException extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenParseException.class);

    // Constructor with only message
    public JwtTokenParseException(String message) {
        super(message);
        logError(message, null);
    }

    // Constructor with message and cause
    public JwtTokenParseException(String message, Throwable cause) {
        super(message, cause);
        logError(message, cause);
    }

    private void logError(String message, Throwable cause) {
        if (cause != null) {
            logger.error("JWT Token Parsing Error: {}. Cause: {}", message, cause.getMessage(), cause);
        } else {
            logger.error("JWT Token Parsing Error: {}", message);
        }
    }
}