package com.car_booking.carbooking.booking;

import com.car_booking.carbooking.car.Car;
import com.car_booking.carbooking.entity.user.AppUser;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "car_booking")
public class CarBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "app_user_id", nullable = false)
    private AppUser user;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private LocalDateTime bookedAt;

    @Column(nullable = false)
    private BookingStatus status;

    public CarBooking(UUID id,
                      AppUser user,
                      Car car,
                      LocalDate startDate,
                      LocalDate endDate,
                      BigDecimal price,
                      BookingStatus status,
                      LocalDateTime bookedAt) {
        this.id = id;
        this.user = user;
        this.car = car;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
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
    }

    public CarBooking() {

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

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LocalDateTime getBookedAt() {
        return bookedAt;
    }

    public BookingStatus getStatus() {
        return status;
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
