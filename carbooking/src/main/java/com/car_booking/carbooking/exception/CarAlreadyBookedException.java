package com.car_booking.carbooking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CarAlreadyBookedException extends RuntimeException {
    public CarAlreadyBookedException(String message) {
        super(message);
    }
}
