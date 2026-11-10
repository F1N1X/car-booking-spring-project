package com.car_booking.carbooking.service;

import com.car_booking.carbooking.car.Brand;
import com.car_booking.carbooking.car.Car;
import com.car_booking.carbooking.exception.CarNotFoundException;
import com.car_booking.carbooking.exception.NoCarsFoundException;
import com.car_booking.carbooking.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    CarRepository carRepository;

    @InjectMocks
    CarService underTest;

    @Test
    void getCarByIdFound() {
        // given
        UUID id = UUID.randomUUID();
        Car given = new Car("B-AB-1234", new BigDecimal("89.00"), Brand.TESLA, true);
        when(carRepository.findById(id)).thenReturn(Optional.of(given));
        // when
        Car actual = underTest.getCarById(id);
        // then
        verify(carRepository).findById(id);
        assertThat(actual).isSameAs(given);
    }


    @Test
    void getCarByIdNotFound() {
        // given
        UUID id = UUID.randomUUID();
        when(carRepository.findById(id)).thenReturn(Optional.empty());
        // when + then
        assertThatThrownBy(()->underTest.getCarById(id))
                .isInstanceOf(CarNotFoundException.class)
                .hasMessageContaining("car not found with id: %s".formatted(id));
    }

    @Test
    void getAllCars() {
        // given
        List<Car> expected = List.of(
                new Car("B-AB-1234", new BigDecimal("89.00"), Brand.TESLA, true),
                new Car("M-XY-5678", new BigDecimal("49.50"), Brand.BMW, false),
                new Car("HH-CD-9012", new BigDecimal("65.00"), Brand.AUDI, false)
        );
        when(carRepository.findAll()).thenReturn(expected);
        // when
        List<Car> actual = underTest.getAllCars();
        // then
        assertThat(actual).isSameAs(expected);
        verify(carRepository).findAll();
    }

    @Test
    void getAllCarsContainNoCarsThrowException() {
        // given
        when(carRepository.findAll()).thenReturn(List.of());
        // when + then
        assertThatThrownBy(() -> underTest.getAllCars())
                .isInstanceOf(NoCarsFoundException.class)
                .hasMessageContaining("no cars found");
    }
}