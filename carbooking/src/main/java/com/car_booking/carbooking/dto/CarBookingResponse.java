package com.car_booking.carbooking.dto;

import com.car_booking.carbooking.car.Brand;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record CarBookingResponse(
        UUID id,
        String userName,
        String carRegNumber,
        Brand carBrand,
        LocalDate startDate,
        LocalDate endDate,
        BigDecimal price,
        LocalDateTime bookedAt
) {}
