package com.car_booking.carbooking.exception;

public class CarBookingNotFoundException extends RuntimeException {
    public CarBookingNotFoundException(String message) {
        super(message);
    }
}
