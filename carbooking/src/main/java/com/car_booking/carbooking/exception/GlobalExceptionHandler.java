package com.car_booking.carbooking.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppUserNotFoundException.class)
    public ResponseEntity<ApiError> handleAppUserNotFound(HttpServletRequest http, AppUserNotFoundException exception) {
        ApiError error = new ApiError(
                http.getContextPath(),
                exception.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(CarAlreadyBookedException.class)
    public ResponseEntity<ApiError> handleCarAlreadyBookedException(HttpServletRequest http, CarAlreadyBookedException exception) {
        ApiError error = new ApiError(
                http.getContextPath(),
                exception.getMessage(),
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now());

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(CarBookingNotFoundException.class)
    public ResponseEntity<ApiError> handleCarBookingNotFoundException(HttpServletRequest http, CarBookingNotFoundException exception) {
        ApiError error = new ApiError(
                http.getContextPath(),
                exception.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(CarNotFoundException.class)
    public ResponseEntity<ApiError> handleInvalidBookingPeriod(HttpServletRequest http, CarNotFoundException exception) {
        ApiError error = new ApiError(
                http.getContextPath(),
                exception.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(NoAvailableCarsException.class)
    public ResponseEntity<ApiError> handleInvalidBookingPeriod(HttpServletRequest http, NoAvailableCarsException exception) {
        ApiError error = new ApiError(
                http.getContextPath(),
                exception.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(NoCarBookingAvailableException.class)
    public ResponseEntity<ApiError> handleInvalidBookingPeriod(HttpServletRequest http, NoCarBookingAvailableException exception) {
        ApiError error = new ApiError(
                http.getContextPath(),
                exception.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(NoCarsFoundException.class)
    public ResponseEntity<ApiError> handleInvalidBookingPeriod(HttpServletRequest http, NoCarsFoundException exception) {
        ApiError error = new ApiError(
                http.getContextPath(),
                exception.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(NoUsersFoundException.class)
    public ResponseEntity<ApiError> handleInvalidBookingPeriod(HttpServletRequest http, NoUsersFoundException exception) {
        ApiError error = new ApiError(
                http.getContextPath(),
                exception.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error);
    }
}
