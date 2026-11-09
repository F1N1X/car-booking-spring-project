package com.car_booking.carbooking.service;


import com.car_booking.carbooking.booking.BookingStatus;
import com.car_booking.carbooking.booking.CarBooking;
import com.car_booking.carbooking.car.Car;
import com.car_booking.carbooking.entity.user.AppUser;
import com.car_booking.carbooking.exception.CarAllReadyBookedException;
import com.car_booking.carbooking.exception.CarBookingNotFoundException;
import com.car_booking.carbooking.exception.NoCarBookingAvailable;
import com.car_booking.carbooking.repository.CarBookingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class CarBookingService {

    private final CarService carService;
    private final CarBookingRepository carBookingRepository;
    private final AppUserService appUserService;

    public CarBookingService(CarService carService,
                             CarBookingRepository carBookingRepository,
                             AppUserService appUserService) {
        this.carService = carService;
        this.carBookingRepository = carBookingRepository;
        this.appUserService = appUserService;
    }

    public void bookCar(UUID userId,
                        String registerNumber,
                        LocalDate startDate,
                        LocalDate endDate) {
        AppUser user = appUserService.getAppUserById(userId);
        Car car = carService.getCarByRegistrationNumber(registerNumber);
        BookingPeriod bookingPeriod = BookingPeriod.forNewBooking(startDate, endDate);

        if (carBookingRepository.existsCarBookingByCar(car))
            throw new CarAllReadyBookedException("car is already booked");


        BigDecimal rentalPrice = car.getRentalPricePerDay().multiply(BigDecimal.valueOf(bookingPeriod.days()));

        CarBooking newBooking = new CarBooking(
                rentalPrice,
                bookingPeriod.start(),
                bookingPeriod.end(),
                car,
                user,
                BookingStatus.ACTIVE);

        carBookingRepository.save(newBooking);
    }

    public void deleteBooking(UUID bookingId) {
        CarBooking carBooking = carBookingRepository.findById(bookingId)
                .orElseThrow(() -> new CarBookingNotFoundException(
                        "booking not found with id %s".formatted(bookingId)));
        carBookingRepository.delete(carBooking);
    }

    public List<AppUser> viewAllUsersWithBookings() {
        List<AppUser> allUserBookedCars = carBookingRepository.getAllUserBookedCars();

        if (allUserBookedCars.isEmpty())
            throw new NoCarBookingAvailable("no booking available");
        return allUserBookedCars;
    }

    public List<CarBooking> viewAllBookings() {
        List<CarBooking> allBookings = carBookingRepository.findAll();

        if (allBookings.isEmpty())
            throw new NoCarBookingAvailable("no booking available");
        return allBookings;
    }

    public List<Car> viewAllAvailableCars() {
        List<Car> allBookedCars = carBookingRepository.getAllBookedCars();
        List<Car> allCars = carService.getAllCars();

        return allCars.stream()
                .filter(c -> !(allBookedCars.contains(c)))
                .toList();
    }

    public List<Car> viewAllAvailableElectricCars() {
        List<Car> allBookedCars = carBookingRepository.getAllBookedCars();
        List<Car> allCars = carService.getAllCars();

        return allCars.stream()
                .filter(c -> !(allBookedCars.contains(c)) && c.isElectric())
                .toList();
    }
}
