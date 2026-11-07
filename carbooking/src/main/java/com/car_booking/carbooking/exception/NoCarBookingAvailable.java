package com.car_booking.carbooking.exception;

public class NoCarBookingAvailable extends RuntimeException {
    public NoCarBookingAvailable(String message) {
        super(message);
    }
}
