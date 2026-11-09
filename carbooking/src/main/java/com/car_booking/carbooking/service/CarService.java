package com.car_booking.carbooking.service;


import com.car_booking.carbooking.car.Car;
import com.car_booking.carbooking.exception.NoCarsFoundException;
import com.car_booking.carbooking.exception.RegistrationNumberNotFoundException;
import com.car_booking.carbooking.repository.CarRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }


    public Car getCarByRegistrationNumber(String registrationNumber) {
        return carRepository.getCarByRegistrationNumber(registrationNumber)
                .orElseThrow( () -> new RegistrationNumberNotFoundException("car not found with registration-number: %s".formatted(registrationNumber)));
    }

    public Car getCarById(UUID id) {
        return carRepository.findById(id)
                .orElseThrow( () -> new RegistrationNumberNotFoundException("car not found with id: %s".formatted(id)));
    }

    public List<Car> getAllCars() {
        List<Car> allCars = carRepository.findAll();

        if (allCars.isEmpty())
            throw new NoCarsFoundException("no cars found");

        return allCars;
    }
}
