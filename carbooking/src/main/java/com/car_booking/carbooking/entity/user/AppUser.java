package com.car_booking.carbooking.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "app_user")
public class AppUser {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    public AppUser(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public AppUser(String name) {
        this.name = name;
        id = UUID.randomUUID();
    }

    public AppUser() {

    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
