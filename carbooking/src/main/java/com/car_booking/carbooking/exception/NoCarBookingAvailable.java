package com.car_booking.carbooking.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoCarBookingAvailable extends RuntimeException {
    public NoCarBookingAvailable(String message) {
        super(message);
    }
}
