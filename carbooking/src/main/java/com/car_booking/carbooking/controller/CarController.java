package com.car_booking.carbooking.controller;


import com.car_booking.carbooking.dto.CarResponse;
import com.car_booking.carbooking.repository.CarBookingRepository;
import com.car_booking.carbooking.repository.CarRepository;
import com.car_booking.carbooking.service.CarBookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("api/v1/cars")
public class CarController {

    private final CarBookingService carBookingService;

    public CarController(CarBookingService carBookingService) {
        this.carBookingService = carBookingService;
    }

    @PostMapping("/available")
    public ResponseEntity<List<CarResponse>> getAllAvailableCars() {
        return ResponseEntity.ok().body(carBookingService.viewAllAvailableCars());
    }

    @PostMapping("/available/electric")
    public ResponseEntity<List<CarResponse>> getAllAvailableElectricCars() {
        return ResponseEntity.ok().body(carBookingService.viewAllAvailableElectricCars());
    }

}
