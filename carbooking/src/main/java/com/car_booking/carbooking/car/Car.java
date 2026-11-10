package com.car_booking.carbooking.car;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;


@Entity
@Table(name = "car")
public class Car {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private UUID id;

        @Column(nullable = false, unique = true)
        private String registrationNumber;

        @Column(nullable = false)
        private BigDecimal rentalPricePerDay;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private Brand brand;

        @Column(nullable = false)
        private boolean isElectric;


        public Car(String registrationNumber,
                   BigDecimal rentalPricePerDay,
                   Brand brand, boolean isElectric) {
            this.registrationNumber = registrationNumber;
            this.rentalPricePerDay = rentalPricePerDay;
            this.brand = brand;
            this.isElectric = isElectric;
        }

        public Car(UUID id,
                   String registrationNumber,
                   BigDecimal rentalPricePerDay,
                   Brand brand,
                   boolean isElectric) {
            this.id = id;
            this.registrationNumber = registrationNumber;
            this.rentalPricePerDay = rentalPricePerDay;
            this.brand = brand;
            this.isElectric = isElectric;
        }

    public Car() {

    }

    public UUID getId() {
            return id;
        }

        public String getRegistrationNumber() {
            return registrationNumber;
        }

        public BigDecimal getRentalPricePerDay() {
            return rentalPricePerDay;
        }

        public Brand getBrand() {
            return brand;
        }

        public boolean isElectric() {
            return isElectric;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Car car = (Car) o;
            return Objects.equals(id, car.id);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(id);
        }

        @Override
        public String toString() {
            return "Car{" +
                    "id=" + id +
                    ", regNumber='" + registrationNumber + '\'' +
                    ", rentalPricePerDay=" + rentalPricePerDay +
                    ", brand=" + brand +
                    ", isElectric=" + isElectric +
                    '}';
        }
    }
