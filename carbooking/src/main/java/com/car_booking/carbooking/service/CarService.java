package com.car_booking.carbooking.service;


import com.car_booking.carbooking.car.Car;
import com.car_booking.carbooking.exception.NoCarsFoundException;
import com.car_booking.carbooking.exception.CarNotFoundException;
import com.car_booking.carbooking.repository.CarRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car getCarById(UUID id) {
        return carRepository.findById(id)
                .orElseThrow( () -> new CarNotFoundException("car not found with id: %s".formatted(id)));
    }

    public List<Car> getAllCars() {
        List<Car> allCars = carRepository.findAll();

        if (allCars.isEmpty())
            throw new NoCarsFoundException("no cars found");

        return allCars;
    }
}
