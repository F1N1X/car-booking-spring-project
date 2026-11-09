package com.car_booking.carbooking.service;

import com.car_booking.carbooking.exception.InvalidBookingPeriodException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public record BookingPeriod(LocalDate start, LocalDate end) {

    public BookingPeriod {
        requireNotNull(start, end);
        requireStartBeforeEnd(start, end);
    }

    public static BookingPeriod forNewBooking(LocalDate start, LocalDate end) {
        BookingPeriod period = new BookingPeriod(start, end);
        if (period.start().isBefore(LocalDate.now()))
            throw new InvalidBookingPeriodException("start is before now");
        return period;
    }

    private void requireNotNull(LocalDate start, LocalDate end) {
        if (start == null || end == null)
            throw new InvalidBookingPeriodException("null value for start or end");
    }

    private void requireStartBeforeEnd(LocalDate start, LocalDate end) {
        if (start.isEqual(end))
            throw new InvalidBookingPeriodException("start date cant be equal end");
        if (start.isAfter(end))
            throw new InvalidBookingPeriodException("start cant be after end date");
    }

    public long days() {
        return ChronoUnit.DAYS.between(start, end);
    }
}
