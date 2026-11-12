package com.car_booking.carbooking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoCarsFoundException extends RuntimeException {
    public NoCarsFoundException(String message) {
        super(message);
    }
}
