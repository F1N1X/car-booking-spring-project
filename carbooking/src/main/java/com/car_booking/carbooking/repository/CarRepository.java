package com.car_booking.carbooking.repository;

import com.car_booking.carbooking.entity.car.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CarRepository extends JpaRepository<Car, UUID> {
}
