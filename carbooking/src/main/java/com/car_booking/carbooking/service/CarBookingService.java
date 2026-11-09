package com.car_booking.carbooking.service;


import com.car_booking.carbooking.booking.BookingStatus;
import com.car_booking.carbooking.booking.CarBooking;
import com.car_booking.carbooking.car.Car;
import com.car_booking.carbooking.dto.AppUserResponse;
import com.car_booking.carbooking.dto.CarBookingRequest;
import com.car_booking.carbooking.dto.CarBookingResponse;
import com.car_booking.carbooking.dto.CarResponse;
import com.car_booking.carbooking.entity.user.AppUser;
import com.car_booking.carbooking.exception.CarAllReadyBookedException;
import com.car_booking.carbooking.exception.CarBookingNotFoundException;
import com.car_booking.carbooking.exception.NoCarBookingAvailable;
import com.car_booking.carbooking.repository.CarBookingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public void bookCar(CarBookingRequest request) {

        BookingPeriod bookingPeriod = BookingPeriod.forNewBooking(request.startDate(), request.endDate());

        AppUser user = appUserService.getAppUserById(request.userId());
        Car car = carService.getCarById(request.carId());

        if (carBookingRepository.existsCarBookingByCar(car))
            throw new CarAllReadyBookedException("car with number: %s is already booked".formatted(car.getRegNumber()));

        CarBooking newBooking = new CarBooking(
                bookingPeriod.getRentalPrice(car.getRentalPricePerDay()),
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

    public List<AppUserResponse> viewAllUsersWithBookings() {
        List<AppUser> allUserBookedCars = carBookingRepository.getAllUserBookedCars();

        if (allUserBookedCars.isEmpty())
            throw new NoCarBookingAvailable("no booking available");

        return allUserBookedCars.stream()
                .map(u -> new AppUserResponse(
                        u.getId(),
                        u.getName()))
                .toList();
    }

    public List<CarBookingResponse> viewAllBookings() {
        List<CarBooking> allBookings = carBookingRepository.findAll();

        if (allBookings.isEmpty())
            throw new NoCarBookingAvailable("no booking available");

        return allBookings.stream()
                .map(b -> new CarBookingResponse(
                        b.getId(),
                        b.getUser().toString(),
                        b.getCar().getRegNumber(),
                        b.getCar().getBrand(),
                        b.getStartDate(),
                        b.getEndDate(),
                        b.getPrice(),
                        b.getStatus(),
                        b.getBookedAt()
                        ))
                .toList();
    }

    public List<CarResponse> viewAllAvailableCars() {
        List<Car> allBookedCars = carBookingRepository.getAllBookedCars();
        List<Car> allCars = carService.getAllCars();

        return allCars.stream()
                .filter(c -> !(allBookedCars.contains(c)))
                .map(c -> new CarResponse(
                        c.getId(),
                        c.getRegNumber(),
                        c.getRentalPricePerDay(),
                        c.getBrand(),
                        c.isElectric()))
                .toList();
    }

    public List<CarResponse> viewAllAvailableElectricCars() {
        List<Car> allBookedCars = carBookingRepository.getAllBookedCars();
        List<Car> allCars = carService.getAllCars();

        return allCars.stream()
                .filter(c -> !(allBookedCars.contains(c)) && c.isElectric())
                .map(c -> new CarResponse(
                        c.getId(),
                        c.getRegNumber(),
                        c.getRentalPricePerDay(),
                        c.getBrand(),
                        c.isElectric()
                )).toList();
    }
}
