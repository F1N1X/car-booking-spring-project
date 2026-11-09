package com.car_booking.carbooking.controller;

import com.car_booking.carbooking.dto.AppUserResponse;
import com.car_booking.carbooking.service.AppUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/v1/users")
public class AppUserController {

    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping
    public ResponseEntity<List<AppUserResponse>> getAllUsers() {
        return ResponseEntity.ok().body(appUserService.getAppUsers());
    }
}
