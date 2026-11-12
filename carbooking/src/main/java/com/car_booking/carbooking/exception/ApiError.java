package com.car_booking.carbooking.exception;

import java.time.LocalDateTime;

public record ApiError(String path,
                       String message,
                       int statusCode,
                       LocalDateTime timestamp) {}
