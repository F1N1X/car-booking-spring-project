package com.car_booking.carbooking.dto;

import com.car_booking.carbooking.car.Brand;

import java.math.BigDecimal;
import java.util.UUID;

public record CarResponse(
        UUID id,
        String regNumber,
        BigDecimal rentalPricePerDay,
        Brand brand,
        boolean isElectric
) {}
