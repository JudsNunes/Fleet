package com.evolutech.core.fleet.infra;

import com.evolutech.core.fleet.exception.BusinessException;
import com.evolutech.core.fleet.exception.ConflictException;
import com.evolutech.core.fleet.exception.ErrorResponse;
import com.evolutech.core.fleet.exception.FieldValidationError;
import com.evolutech.core.fleet.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

 
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex,
            WebRequest request) {
        
        log.warn("Validation exception occurred: {}", ex.getMessage());
        
        List<FieldValidationError> fieldErrors = new ArrayList<>();
        
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            Object rejectedValue = ((FieldError) error).getRejectedValue();
            
            fieldErrors.add(FieldValidationError.builder()
                    .field(fieldName)
                    .message(errorMessage)
                    .rejectedValue(rejectedValue)
                    .build());
        });

        ErrorResponse response = ErrorResponse.builder()
                .code("VALIDATION_FAILED")
                .message("Request validation failed")
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false).replace("uri=", ""))
                .fieldErrors(fieldErrors)
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(
            NotFoundException ex,
            WebRequest request) {

        log.warn("Resource not found: {}", ex.getMessage());

        ErrorResponse response = ErrorResponse.builder()
                .code("NOT_FOUND")
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflictException(
            ConflictException ex,
            WebRequest request) {

        log.warn("Conflict: {}", ex.getMessage());

        ErrorResponse response = ErrorResponse.builder()
                .code("CONFLICT")
                .message(ex.getMessage())
                .status(HttpStatus.CONFLICT.value())
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(
            BusinessException ex,
            WebRequest request) {
        
        log.error("Business exception occurred: {}", ex.getMessage());
        
        String code = "BUSINESS_ERROR";
        HttpStatus status = HttpStatus.BAD_REQUEST;

        if (ex.getMessage().contains("not found")) {
            code = "NOT_FOUND";
            status = HttpStatus.NOT_FOUND;
        } else if (ex.getMessage().contains("already exists")) {
            code = "CONFLICT";
            status = HttpStatus.CONFLICT;
        } else if (ex.getMessage().contains("Vehicle")) {
            code = "VEHICLE_ERROR";
            status = HttpStatus.BAD_REQUEST;
        } else if (ex.getMessage().contains("Maintenance")) {
            code = "MAINTENANCE_ERROR";
            status = HttpStatus.BAD_REQUEST;
        }

        ErrorResponse response = ErrorResponse.builder()
                .code(code)
                .message(ex.getMessage())
                .status(status.value())
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        return new ResponseEntity<>(response, status);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex,
            WebRequest request) {
        
        log.warn("Illegal argument exception: {}", ex.getMessage());
        
        String code = "INVALID_ARGUMENT";
        HttpStatus status = HttpStatus.BAD_REQUEST;

        if (ex.getMessage().contains("not found")) {
            code = "RESOURCE_NOT_FOUND";
            status = HttpStatus.NOT_FOUND;
        }

        ErrorResponse response = ErrorResponse.builder()
                .code(code)
                .message(ex.getMessage())
                .status(status.value())
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        return new ResponseEntity<>(response, status);
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            WebRequest request) {
        
        log.warn("Type mismatch: {} cannot be converted to {}", 
                ex.getValue(), ex.getRequiredType().getSimpleName());
        
        String message = String.format(
                "Parameter '%s' has invalid value '%s'. Expected type: %s",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()
        );

        ErrorResponse response = ErrorResponse.builder()
                .code("TYPE_MISMATCH")
                .message(message)
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false).replace("uri=", ""))
                .detail("Valid enum values: PENDING, IN_PROGRESS, COMPLETED (for maintenance status)")
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

 
    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(
            org.springframework.dao.DataIntegrityViolationException ex,
            WebRequest request) {
        
        log.error("Data integrity violation: {}", ex.getMessage());
        
        String code = "DATA_INTEGRITY_VIOLATION";
        String message = "Database constraint violation";
        HttpStatus status = HttpStatus.CONFLICT;

        if (ex.getMessage().contains("uk_plate") || ex.getMessage().contains("plate")) {
            code = "PLATE_DUPLICATE";
            message = "This vehicle plate already exists";
        } else if (ex.getMessage().contains("fk_vehicle_id") || ex.getMessage().contains("vehicle_id")) {
            code = "VEHICLE_NOT_FOUND";
            message = "Referenced vehicle does not exist";
            status = HttpStatus.NOT_FOUND;
        } else if (ex.getMessage().contains("CHECK")) {
            code = "CONSTRAINT_VIOLATION";
            message = "Value violates database constraints (e.g., negative cost, invalid date range)";
            status = HttpStatus.BAD_REQUEST;
        }

        ErrorResponse response = ErrorResponse.builder()
                .code(code)
                .message(message)
                .status(status.value())
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false).replace("uri=", ""))
                .detail(ex.getMessage())
                .build();

        return new ResponseEntity<>(response, status);
    }

 
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponse> handleNullPointerException(
            NullPointerException ex,
            WebRequest request) {
        
        log.error("Null pointer exception: ", ex);
        
        ErrorResponse response = ErrorResponse.builder()
                .code("INTERNAL_ERROR")
                .message("An unexpected error occurred (null reference)")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false).replace("uri=", ""))
                .detail(ex.getMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
            Exception ex,
            WebRequest request) {
        
        log.error("Unexpected error: ", ex);
        
        ErrorResponse response = ErrorResponse.builder()
                .code("INTERNAL_SERVER_ERROR")
                .message("An unexpected error occurred")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false).replace("uri=", ""))
                .detail(ex.getMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
