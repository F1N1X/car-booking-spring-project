package com.car_booking.carbooking.exception;

public class AppUserNotFoundException extends RuntimeException{
    public AppUserNotFoundException(String message) {
        super(message);
    }
}
