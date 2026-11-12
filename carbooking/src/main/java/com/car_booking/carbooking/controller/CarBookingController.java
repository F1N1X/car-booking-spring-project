package com.car_booking.carbooking.controller;


import com.car_booking.carbooking.booking.CarBooking;
import com.car_booking.carbooking.dto.CarBookingRequest;
import com.car_booking.carbooking.dto.CarBookingResponse;
import com.car_booking.carbooking.service.CarBookingService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/api/v1/bookings")
public class CarBookingController {

    private final CarBookingService carBookingService;

    public CarBookingController(CarBookingService carBookingService) {
        this.carBookingService = carBookingService;
    }

    @PostMapping
    public ResponseEntity<Void> bookCar(
            @Valid @RequestBody CarBookingRequest request) {
        carBookingService.bookCar(request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CarBookingResponse>> getAllBooking() {
        return ResponseEntity.ok().body(carBookingService.viewAllBookings());
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> deleteBookingById(
            @PathVariable("bookingId") UUID id) {

        carBookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}
