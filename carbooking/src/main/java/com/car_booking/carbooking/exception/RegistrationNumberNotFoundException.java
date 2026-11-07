package com.car_booking.carbooking.exception;

public class RegistrationNumberNotFoundException extends RuntimeException {
    public RegistrationNumberNotFoundException(String message) {
        super(message);
    }
}
