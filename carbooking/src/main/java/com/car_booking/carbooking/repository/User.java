package com.car_booking.carbooking.repository;

import com.car_booking.carbooking.entity.booking.CarBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface User extends JpaRepository<User, UUID> {
    List<CarBooking> findByUserId(UUID userId);

}
