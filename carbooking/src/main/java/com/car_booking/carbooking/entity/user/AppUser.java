package com.car_booking.carbooking.entity.user;

import java.util.UUID;

public class AppUser {

    private static final long serialVersionUID = 1L;
    private UUID id;
    private String name;

    public AppUser(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public AppUser(String name) {
        this.name = name;
        id = UUID.randomUUID();
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
