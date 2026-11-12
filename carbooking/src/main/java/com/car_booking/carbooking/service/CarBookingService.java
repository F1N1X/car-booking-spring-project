package com.car_booking.carbooking.service;


import com.car_booking.carbooking.booking.CarBooking;
import com.car_booking.carbooking.car.Car;
import com.car_booking.carbooking.dto.AppUserResponse;
import com.car_booking.carbooking.dto.CarBookingRequest;
import com.car_booking.carbooking.dto.CarBookingResponse;
import com.car_booking.carbooking.dto.CarResponse;
import com.car_booking.carbooking.entity.user.AppUser;
import com.car_booking.carbooking.exception.*;
import com.car_booking.carbooking.repository.CarBookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CarBookingService {

    private final CarService carService;
    private final CarBookingRepository carBookingRepository;
    private final AppUserService appUserService;
    private static final Logger log = LoggerFactory.getLogger(CarBookingService.class);

    public CarBookingService(CarService carService,
                             CarBookingRepository carBookingRepository,
                             AppUserService appUserService) {
        this.carService = carService;
        this.carBookingRepository = carBookingRepository;
        this.appUserService = appUserService;
    }

    public void bookCar(CarBookingRequest request) {

        BookingPeriod bookingPeriod = BookingPeriod.forNewBooking(request.startDate(), request.endDate());

        AppUser user = appUserService.getAppUserById(request.userId());
        Car car = carService.getCarById(request.carId());

        if (carBookingRepository.existsByCarAndStartDateBeforeAndEndDateAfter(
                car, bookingPeriod.end(), bookingPeriod.start()))
            throw new CarAlreadyBookedException("car with number: %s is already booked".formatted(car.getRegistrationNumber()));

        CarBooking newBooking = new CarBooking(
                bookingPeriod.getRentalPrice(car.getRentalPricePerDay()),
                bookingPeriod.start(),
                bookingPeriod.end(),
                car,
                user);

        CarBooking savedBooking = carBookingRepository.save(newBooking);
        log.info("Booking created: id = {}, carId = {}, userId = {}",
                savedBooking.getId(), car.getId(), user.getId());
    }

    public void deleteBooking(UUID bookingId) {
        CarBooking carBooking = carBookingRepository.findById(bookingId)
                .orElseThrow(() -> new CarBookingNotFoundException(
                        "booking not found with id %s".formatted(bookingId)));
        carBookingRepository.delete(carBooking);
        log.info("Booking deleted: id = {}", bookingId);
    }

    public List<AppUserResponse> viewAllUsersWithBookings() {
        List<AppUser> allUserBookedCars = carBookingRepository.getAllUserBookedCars();

        if (allUserBookedCars.isEmpty())
            throw new NoCarBookingAvailableException("no booking available");

        return allUserBookedCars.stream()
                .map(u -> new AppUserResponse(
                        u.getId(),
                        u.getName()))
                .toList();
    }

    public List<CarBookingResponse> viewAllBookings() {
        List<CarBooking> allBookings = carBookingRepository.findAll();

        if (allBookings.isEmpty())
            throw new NoCarBookingAvailableException("no booking available");

        return allBookings.stream()
                .map(b -> new CarBookingResponse(
                        b.getId(),
                        b.getUser().getName(),
                        b.getCar().getRegistrationNumber(),
                        b.getCar().getBrand(),
                        b.getStartDate(),
                        b.getEndDate(),
                        b.getPrice(),
                        b.getBookedAt()
                        ))
                .toList();
    }

    public List<CarResponse> viewAllAvailableCars() {
        List<Car> allBookedCars = carBookingRepository.getAllBookedCars();
        List<Car> allCars = carService.getAllCars();


        List<CarResponse> availableCars = allCars.stream()
                .filter(c -> !(allBookedCars.contains(c)))
                .map(c -> new CarResponse(
                        c.getId(),
                        c.getRegistrationNumber(),
                        c.getRentalPricePerDay(),
                        c.getBrand(),
                        c.isElectric()))
                .toList();

        if (availableCars.isEmpty())
            throw new NoAvailableCarsException("no cars available for booking");

        return availableCars;
    }

    public List<CarResponse> viewAllAvailableElectricCars() {
        List<Car> allBookedCars = carBookingRepository.getAllBookedCars();
        List<Car> allCars = carService.getAllCars();

        List<CarResponse> availableElectroCars = allCars.stream()
                .filter(c -> !(allBookedCars.contains(c)) && c.isElectric())
                .map(c -> new CarResponse(
                        c.getId(),
                        c.getRegistrationNumber(),
                        c.getRentalPricePerDay(),
                        c.getBrand(),
                        c.isElectric()
                )).toList();

        if (availableElectroCars.isEmpty())
            throw new NoAvailableCarsException("no cars available for booking");

        return availableElectroCars;
    }
}
