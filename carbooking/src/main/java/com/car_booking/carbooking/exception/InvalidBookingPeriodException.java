package com.car_booking.carbooking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidBookingPeriodException extends RuntimeException {
    public InvalidBookingPeriodException(String message) {
        super(message);
    }
}
