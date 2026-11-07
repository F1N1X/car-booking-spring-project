package com.car_booking.carbooking.exception;

public class NoCarsFoundException extends RuntimeException {
    public NoCarsFoundException(String message) {
        super(message);
    }
}
