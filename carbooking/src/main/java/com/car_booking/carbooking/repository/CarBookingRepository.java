package com.car_booking.carbooking.repository;

import com.car_booking.carbooking.entity.booking.CarBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CarBookingRepository extends JpaRepository<CarBooking, UUID> {
}
