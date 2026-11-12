package com.car_booking.carbooking.dto;


import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record CarBookingRequest(
                                @NotNull UUID userId,
                                @NotNull UUID carId,
                                @FutureOrPresent LocalDate startDate,
                                @Future LocalDate endDate) {}
