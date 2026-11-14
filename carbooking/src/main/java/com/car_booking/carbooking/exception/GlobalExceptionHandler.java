package com.car_booking.carbooking.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(AppUserNotFoundException.class)
    public ResponseEntity<ApiError> handleAppUserNotFound(HttpServletRequest http, AppUserNotFoundException exception) {
        ApiError error = new ApiError(
                http.getRequestURI(),
                exception.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now());

        log.warn("{}: {}", exception.getClass().getSimpleName(), exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(CarAlreadyBookedException.class)
    public ResponseEntity<ApiError> handleCarAlreadyBooked(HttpServletRequest http, CarAlreadyBookedException exception) {
        ApiError error = new ApiError(
                http.getRequestURI(),
                exception.getMessage(),
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now());

        log.warn("{}: {}", exception.getClass().getSimpleName(), exception.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(CarBookingNotFoundException.class)
    public ResponseEntity<ApiError> handleCarBookingNotFound(HttpServletRequest http, CarBookingNotFoundException exception) {
        ApiError error = new ApiError(
                http.getRequestURI(),
                exception.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now());

        log.warn("{}: {}", exception.getClass().getSimpleName(), exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(CarNotFoundException.class)
    public ResponseEntity<ApiError> handleCarNotFound(HttpServletRequest http, CarNotFoundException exception) {
        ApiError error = new ApiError(
                http.getRequestURI(),
                exception.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now());

        log.warn("{}: {}", exception.getClass().getSimpleName(), exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(NoAvailableCarsException.class)
    public ResponseEntity<ApiError> handleNoAvailableCars(HttpServletRequest http, NoAvailableCarsException exception) {
        ApiError error = new ApiError(
                http.getRequestURI(),
                exception.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now());

        log.warn("{}: {}", exception.getClass().getSimpleName(), exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(NoCarBookingAvailableException.class)
    public ResponseEntity<ApiError> handleNoCarBookingAvailable(HttpServletRequest http, NoCarBookingAvailableException exception) {
        ApiError error = new ApiError(
                http.getRequestURI(),
                exception.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now());

        log.warn("{}: {}", exception.getClass().getSimpleName(), exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(NoCarsFoundException.class)
    public ResponseEntity<ApiError> handleNoCarsFound(HttpServletRequest http, NoCarsFoundException exception) {
        ApiError error = new ApiError(
                http.getRequestURI(),
                exception.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now());

        log.warn("{}: {}", exception.getClass().getSimpleName(), exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(NoUsersFoundException.class)
    public ResponseEntity<ApiError> handleNoUsersFound(HttpServletRequest http, NoUsersFoundException exception) {
        ApiError error = new ApiError(
                http.getRequestURI(),
                exception.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now());

        log.warn("{}: {}", exception.getClass().getSimpleName(), exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(InvalidBookingPeriodException.class)
    public ResponseEntity<ApiError> handleInvalidBookingPeriod(HttpServletRequest http, InvalidBookingPeriodException exception) {
        ApiError error = new ApiError(
                http.getRequestURI(),
                exception.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now());

        log.warn("{}: {}", exception.getClass().getSimpleName(), exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(HttpServletRequest http, MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ApiError error = new ApiError(
                http.getRequestURI(),
                message,
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now());

        log.warn("{}: {}", exception.getClass().getSimpleName(), message);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleHttpMessageNotReadable(HttpServletRequest http, HttpMessageNotReadableException exception) {
        ApiError error = new ApiError(
                http.getRequestURI(),
                "Request-Body is not valid or non valid JSON-Format",
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now());

        log.error("Unexpected error on {}", http.getRequestURI(), exception);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleMethodArgumentTypeMismatch(HttpServletRequest http, MethodArgumentTypeMismatchException exception) {
        ApiError error = new ApiError(
                http.getRequestURI(),
                String.format("Parameter %s has invalid value %s",exception.getName(),exception.getValue()),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now());

        log.error("Unexpected error on {}", http.getRequestURI(), exception);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiError> handleHttpRequestMethodNotSupported(HttpServletRequest http, HttpRequestMethodNotSupportedException exception) {
        ApiError error = new ApiError(
                http.getRequestURI(),
                String.format("Method is not supported: %s",exception.getMethod()),
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                LocalDateTime.now());

        log.error("Unexpected error on {}", http.getRequestURI(), exception);

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(error);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiError> handleNoResourceFound(HttpServletRequest http, NoResourceFoundException exception) {
        ApiError error = new ApiError(
                http.getRequestURI(),
                String.format("Path not found %s",exception.getResourcePath()),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now());

        log.error("Unexpected error on {}", http.getRequestURI(), exception);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiError> handleNoHandlerFound(HttpServletRequest http, NoHandlerFoundException exception) {
        ApiError error = new ApiError(
                http.getRequestURI(),
                String.format("No endpoint found for  %s",exception.getRequestURL()),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now());

        log.error("Unexpected error on {}", http.getRequestURI(), exception);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(HttpServletRequest http, ConstraintViolationException exception) {
        ApiError error = new ApiError(
                http.getRequestURI(),
                String.format("A constraint is violated: %s", exception.getMessage()),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now());

        log.error("Unexpected error on {}", http.getRequestURI(), exception);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnexpected(HttpServletRequest http, Exception exception) {
        ApiError error = new ApiError(
                http.getRequestURI(),
                "An unexpected error occurred.",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now());

        log.error("Unexpected error on {}", http.getRequestURI(), exception);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

}
