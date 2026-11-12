package com.car_booking.carbooking.service;

import com.car_booking.carbooking.booking.CarBooking;
import com.car_booking.carbooking.car.Brand;
import com.car_booking.carbooking.car.Car;
import com.car_booking.carbooking.dto.AppUserResponse;
import com.car_booking.carbooking.dto.CarBookingRequest;
import com.car_booking.carbooking.dto.CarBookingResponse;
import com.car_booking.carbooking.dto.CarResponse;
import com.car_booking.carbooking.entity.user.AppUser;
import com.car_booking.carbooking.exception.*;
import com.car_booking.carbooking.repository.CarBookingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CarBookingServiceTest {

    @Mock
    CarService carService;

    @Mock
    CarBookingRepository carBookingRepository;

    @Mock
    AppUserService appUserService;

    @InjectMocks
    CarBookingService underTest;

    @Test
    void bookCarThrowsWhenStartIsInPast() {
        // given
        CarBookingRequest request = new CarBookingRequest(
                UUID.randomUUID(),
                UUID.randomUUID(),
                LocalDate.now().minusDays(1),
                LocalDate.now().plusDays(3)
        );

        // when + then
        assertThatThrownBy(() -> underTest.bookCar(request))
                .isInstanceOf(InvalidBookingPeriodException.class)
                .hasMessageContaining("start is before now");

        verifyNoInteractions(appUserService, carService, carBookingRepository);
    }

    @Test
    void bookCarWithAllReadyBookedCarThrowException() {
        // given
        CarBookingRequest request = new CarBookingRequest(
                UUID.randomUUID(),
                UUID.randomUUID(),
                LocalDate.now(),
                LocalDate.now().plusDays(3)
        );

        Car car = new Car(request.carId(), "B-AB-1234", new BigDecimal("89.00"), Brand.TESLA, true);
        AppUser user = new AppUser(request.userId(), "test");
        when(appUserService.getAppUserById(request.userId())).thenReturn(user);
        when(carService.getCarById(request.carId())).thenReturn(car);
        when(carBookingRepository.existsCarBookingByCar(car)).thenReturn(true);
        // when + then
        assertThatThrownBy(() -> underTest.bookCar(request))
                .isInstanceOf(CarAllReadyBookedException.class)
                .hasMessageContaining("car with number: %s is already booked".formatted(car.getRegistrationNumber()));
        verify(carBookingRepository,
                never()).save(any());
    }

    @Test
    void bookCar() {
        // given
        CarBookingRequest request = new CarBookingRequest(
                UUID.randomUUID(),
                UUID.randomUUID(),
                LocalDate.now(),
                LocalDate.now().plusDays(3)
        );
        Car car = new Car(request.carId(), "B-AB-1234", new BigDecimal("89.00"), Brand.TESLA, true);
        AppUser user = new AppUser(request.userId(), "test");
        when(appUserService.getAppUserById(request.userId())).thenReturn(user);
        when(carService.getCarById(request.carId())).thenReturn(car);
        when(carBookingRepository.existsCarBookingByCar(car)).thenReturn(false);
        // when
        underTest.bookCar(request);
        ArgumentCaptor<CarBooking> captor =
                ArgumentCaptor.forClass(CarBooking.class);
        verify(carBookingRepository).save(captor.capture());
        CarBooking saved = captor.getValue();
        // then
        assertThat(saved.getPrice()).isEqualByComparingTo("267.00");
        assertThat(saved.getStartDate()).isEqualTo(request.startDate());
        assertThat(saved.getEndDate()).isEqualTo(request.endDate());
        assertThat(saved.getCar()).isSameAs(car);
        assertThat(saved.getUser()).isSameAs(user);
    }

    @Test
    void deleteBooking() {
        // given
        Car car = new Car(UUID.randomUUID(), "B-AB-1234", new BigDecimal("89.00"), Brand.TESLA, true);
        AppUser user = new AppUser(UUID.randomUUID(), "test");
        CarBooking booking = new CarBooking(
                UUID.randomUUID(),
                user,
                car,
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(4),
                new BigDecimal("267.00"),
                LocalDateTime.now()
        );
        when(carBookingRepository.findById(booking.getId())).thenReturn(Optional.of(booking));
        // when
        underTest.deleteBooking(booking.getId());
        // then
        verify(carBookingRepository).delete(booking);
    }

    @Test
    void deleteBookingCarBookingNotFound() {
        // given
        UUID id = UUID.randomUUID();

        when(carBookingRepository.findById(id)).thenReturn(Optional.empty());
        // when + then
        assertThatThrownBy(() -> underTest.deleteBooking(id))
                .isInstanceOf(CarBookingNotFoundException.class)
                .hasMessageContaining("booking not found with id %s".formatted(id));
        // then
        verify(carBookingRepository,never()).delete(any(CarBooking.class));
    }

    @Test
    void viewAllUsersWithBookings() {
        // given
        AppUser user1 = new AppUser(UUID.randomUUID(), "test1");
        AppUser user2 = new AppUser(UUID.randomUUID(), "test2");
        List<AppUser> usersWithBookings = List.of(user1, user2);
        List<AppUserResponse> expected = List.of(
                new AppUserResponse(user1.getId(), user1.getName()),
                new AppUserResponse(user2.getId(), user2.getName())
        );
        when(carBookingRepository.getAllUserBookedCars()).thenReturn(usersWithBookings);
        // when
        List<AppUserResponse> actual = underTest.viewAllUsersWithBookings();
        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void viewAllUsersWithBookingsNoCarBookingAvailable() {
        // given
        when(carBookingRepository.getAllUserBookedCars()).thenReturn(List.of());
        // when + then
        assertThatThrownBy(() -> underTest.viewAllUsersWithBookings())
                .isInstanceOf(NoCarBookingAvailable.class)
                .hasMessageContaining("no booking available");

        verify(carBookingRepository).getAllUserBookedCars();
        verifyNoMoreInteractions(carBookingRepository);
    }

    @Test
    void viewAllBookings() {
        // given
        Car car1 = new Car(UUID.randomUUID(), "B-AB-1234", new BigDecimal("89.00"), Brand.TESLA, true);
        Car car2 = new Car(UUID.randomUUID(), "M-XY-5678", new BigDecimal("49.50"), Brand.BMW, false);
        AppUser user1 = new AppUser(UUID.randomUUID(), "test1");
        AppUser user2 = new AppUser(UUID.randomUUID(), "test2");

        CarBooking booking1 = new CarBooking(
                UUID.randomUUID(),
                user1,
                car1,
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(4),
                new BigDecimal("267.00"),
                LocalDateTime.now()
        );

        CarBooking booking2 = new CarBooking(
                UUID.randomUUID(),
                user2,
                car2,
                LocalDate.now().plusDays(2),
                LocalDate.now().plusDays(5),
                new BigDecimal("148.50"),
                LocalDateTime.now()
        );
        List<CarBooking> bookings = List.of(booking1, booking2);
        List<CarBookingResponse> expected = List.of(
                new CarBookingResponse(
                        booking1.getId(),
                        booking1.getUser().getName(),
                        booking1.getCar().getRegistrationNumber(),
                        booking1.getCar().getBrand(),
                        booking1.getStartDate(),
                        booking1.getEndDate(),
                        booking1.getPrice(),
                        booking1.getBookedAt()
                ),
                new CarBookingResponse(
                        booking2.getId(),
                        booking2.getUser().getName(),
                        booking2.getCar().getRegistrationNumber(),
                        booking2.getCar().getBrand(),
                        booking2.getStartDate(),
                        booking2.getEndDate(),
                        booking2.getPrice(),
                        booking2.getBookedAt()
                )
        );
        when(carBookingRepository.findAll()).thenReturn(bookings);
        // when
        List<CarBookingResponse> actual = underTest.viewAllBookings();
        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void viewAllBookingsNoBookingAvailableException() {
        when(carBookingRepository.findAll()).thenReturn(List.of());
        assertThatThrownBy(() -> underTest.viewAllBookings())
                .isInstanceOf(NoCarBookingAvailable.class)
                .hasMessageContaining("no booking available");
    }

    @Test
    void viewAllAvailableCars() {
        // given
        Car car1 = new Car(UUID.randomUUID(), "B-AB-1234", new BigDecimal("89.00"), Brand.TESLA, true);
        Car car2 = new Car(UUID.randomUUID(), "M-XY-5678", new BigDecimal("49.50"), Brand.BMW, false);
        Car car3 = new Car(UUID.randomUUID(), "HH-CD-9012", new BigDecimal("65.00"), Brand.AUDI, false);
        List<Car> allCars = List.of(car1, car2, car3);
        List<Car> allBookedCars = List.of(car2);
        List<CarResponse> expected = List.of(
                new CarResponse(
                        car1.getId(),
                        car1.getRegistrationNumber(),
                        car1.getRentalPricePerDay(),
                        car1.getBrand(),
                        car1.isElectric()),
                new CarResponse(
                        car3.getId(),
                        car3.getRegistrationNumber(),
                        car3.getRentalPricePerDay(),
                        car3.getBrand(),
                        car3.isElectric())
        );
        when(carBookingRepository.getAllBookedCars()).thenReturn(allBookedCars);
        when(carService.getAllCars()).thenReturn(allCars);
        // when
        List<CarResponse> actual = underTest.viewAllAvailableCars();
        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void viewAllAvailableCarsNoAvailableCarsException() {
        Car car1 = new Car(UUID.randomUUID(), "B-AB-1234", new BigDecimal("89.00"), Brand.TESLA, true);
        Car car2 = new Car(UUID.randomUUID(), "M-XY-5678", new BigDecimal("49.50"), Brand.BMW, false);

        List<Car> allCars = List.of(car1, car2);

        when(carBookingRepository.getAllBookedCars()).thenReturn(allCars);
        when(carService.getAllCars()).thenReturn(allCars);

        assertThatThrownBy(() -> underTest.viewAllAvailableCars())
                .isInstanceOf(NoAvailableCarsException.class)
                .hasMessageContaining("no cars available for booking");
    }

    @Test
    void viewAllAvailableElectricCars() {
        // given
        Car electricFree   = new Car(UUID.randomUUID(), "B-AB-1234", new BigDecimal("89.00"), Brand.TESLA, true);
        Car electricBooked = new Car(UUID.randomUUID(), "B-EV-4321", new BigDecimal("75.00"), Brand.BYD, true);

        List<Car> allCars = List.of(electricFree, electricBooked);
        List<Car> allBookedCars = List.of(electricBooked);

        List<CarResponse> expected = List.of(
                new CarResponse(
                        electricFree.getId(),
                        electricFree.getRegistrationNumber(),
                        electricFree.getRentalPricePerDay(),
                        electricFree.getBrand(),
                        electricFree.isElectric())
        );

        when(carBookingRepository.getAllBookedCars()).thenReturn(allBookedCars);
        when(carService.getAllCars()).thenReturn(allCars);
        // when
        List<CarResponse> actual = underTest.viewAllAvailableElectricCars();
        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void viewAllAvailableElectricCarsNoAvailableCarsException() {
        Car electricFree   = new Car(UUID.randomUUID(), "B-AB-1234", new BigDecimal("89.00"), Brand.TESLA, true);
        Car electricBooked = new Car(UUID.randomUUID(), "B-EV-4321", new BigDecimal("75.00"), Brand.BYD, true);

        List<Car> allCars = List.of(electricFree, electricBooked);

        when(carBookingRepository.getAllBookedCars()).thenReturn(allCars);
        when(carService.getAllCars()).thenReturn(allCars);

        assertThatThrownBy(() -> underTest.viewAllAvailableElectricCars())
                .isInstanceOf(NoAvailableCarsException.class)
                .hasMessageContaining("no cars available for booking");
    }
}