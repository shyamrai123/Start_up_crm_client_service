package com.example.Start_up_crm_client_service.exception;

import com.example.Start_up_crm_client_service.dto.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // ── 1. Validation errors (@Valid failures) ────────────────────
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            fieldErrors.put(field, message);
        });

        logger.warn("Validation failed: {}", fieldErrors);

        ApiResponse<Map<String, String>> response = new ApiResponse<>(
                400, false, "Validation Failed", fieldErrors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // ── 2. Section locked (permission system) ─────────────────────
    @ExceptionHandler(SectionLockedException.class)
    public ResponseEntity<ApiResponse<Void>> handleSectionLocked(SectionLockedException ex) {
        logger.warn("Section locked: {}", ex.getMessage());
        ApiResponse<Void> response = ApiResponse.error(ex.getMessage(), HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    // ── 3. CustomException ────────────────────────────────────────
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException ex) {
        logger.error("CustomException: {}", ex.getMessage());
        ApiResponse<Void> response = ApiResponse.error(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // ── 4. JWT Token Expired ──────────────────────────────────────
    @ExceptionHandler(JwtTokenExpiredException.class)
    public ResponseEntity<ApiResponse<Void>> handleJwtTokenExpiredException(JwtTokenExpiredException ex) {
        logger.error("JWT Token Expired: {}", ex.getMessage());
        ApiResponse<Void> response = ApiResponse.error(
                "JWT Token has expired. Please re-authenticate.",
                HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    // ── 5. Access Denied ──────────────────────────────────────────
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDeniedException(AccessDeniedException ex) {
        logger.error("Access Denied: {}", ex.getMessage());
        ApiResponse<Void> response = ApiResponse.error("Access Denied", HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    // ── 6. Catch-all (always last) ────────────────────────────────
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(Exception ex) {
        logger.error("Unexpected error: ", ex);
        ApiResponse<Void> response = ApiResponse.error(
                "An unexpected error occurred",
                HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}