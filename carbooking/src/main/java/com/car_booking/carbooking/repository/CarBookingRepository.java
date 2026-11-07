package com.car_booking.carbooking.repository;

import com.car_booking.carbooking.booking.CarBooking;
import com.car_booking.carbooking.car.Car;
import com.car_booking.carbooking.entity.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CarBookingRepository extends JpaRepository<CarBooking, UUID> {
    boolean existsCarBookingByCar(Car car);

    @Query("SELECT DISTINCT b.user FROM CarBooking b")
    List<AppUser> getAllUserBookedCars();

    @Query("SELECT b.car FROM CarBooking b")
    List<Car> getAllBookedCars();
}
