package com.car_booking.carbooking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CarAllReadyBookedException extends RuntimeException {
    public CarAllReadyBookedException(String message) {
        super(message);
    }
}
