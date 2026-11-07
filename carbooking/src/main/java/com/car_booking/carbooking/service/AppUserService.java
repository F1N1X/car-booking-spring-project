package com.car_booking.carbooking.service;

import com.car_booking.carbooking.entity.user.AppUser;
import com.car_booking.carbooking.exception.AppUserNotFoundException;
import com.car_booking.carbooking.exception.NoUsersFoundException;
import com.car_booking.carbooking.repository.AppUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;

    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public AppUser getAppUserById(UUID id) {
       return appUserRepository.findById(id)
                .orElseThrow(() -> new AppUserNotFoundException("user not found with id %s".formatted(id)));
    }

    public List<AppUser> getAppUsers() {
        List<AppUser> allAppUsers = appUserRepository.findAll();

        if (allAppUsers.isEmpty()) {
            throw new NoUsersFoundException("no user found");
        }
        return allAppUsers;
    }


}
