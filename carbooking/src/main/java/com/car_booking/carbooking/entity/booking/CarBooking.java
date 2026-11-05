package com.car_booking.carbooking.entity.booking;

import com.car_booking.carbooking.entity.car.Car;
import com.car_booking.carbooking.entity.user.AppUser;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class CarBooking {
    @Serial
    private static final long serialVersionUID = 1L;


    private UUID id;
    private AppUser user;
    private Car car;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal price;
    private BookingStatus status;
    private LocalDateTime bookedAt;

    public CarBooking(UUID id, AppUser user, Car car, LocalDate startDate, LocalDate endDate, BigDecimal price, BookingStatus status, LocalDateTime bookedAt) {
        this.id = id;
        this.user = user;
        this.car = car;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.status = status;
        this.bookedAt = bookedAt;
    }

    public CarBooking(BigDecimal price,
                      LocalDate startDate,
                      LocalDate endDate,
                      Car car,
                      AppUser user,
                      BookingStatus status) {
        this.price = price;
        this.bookedAt = LocalDateTime.now();
        this.endDate = endDate;
        this.startDate = startDate;
        this.car = car;
        this.user = user;
        this.id = UUID.randomUUID();
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public AppUser getUser() {
        return user;
    }

    public Car getCar() {
        return car;
    }

    @Override
    public String toString() {
        return  "id=" + id +
                "\ncom.user=" + user +
                "\ncom.car=" + car +
                "\nstartDate=" + startDate +
                "\tendDate=" + endDate +
                "\nprice=" + price +
                "\nbookedAt=" + bookedAt;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarBooking that = (CarBooking) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
