package com.car_booking.carbooking.service;

import com.car_booking.carbooking.dto.AppUserResponse;
import com.car_booking.carbooking.entity.user.AppUser;
import com.car_booking.carbooking.exception.NoUsersFoundException;
import com.car_booking.carbooking.repository.AppUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppUserServiceTest {

    @Mock
    AppUserRepository appUserRepository;

    @InjectMocks
    AppUserService underTest;



    @Test
    void getAppUserByIdWhenFound() {
        // given
        UUID id = UUID.randomUUID();
        AppUser given = new AppUser("Test");
        // when
        when(appUserRepository.findById(id))
                .thenReturn(Optional.of(given));
        AppUser actual = underTest.getAppUserById(id);
        // then
        verify(appUserRepository).findById(id);
        assertThat(actual).isEqualTo(given);
    }


    @Test
    void getAppUserByIdNotFound() {
        UUID id = UUID.randomUUID();
        when(appUserRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.getAppUserById(id))
                .isInstanceOf(NoUsersFoundException.class)
                .hasMessageContaining("user not found with id %s".formatted(id));
    }

    @Test
    void getAppUsers() {
        // given
        AppUser user1 = new AppUser("Test1");
        AppUser user2 = new AppUser("Test2");
        when(appUserRepository.findAll()).thenReturn(List.of(user1, user2));
        // when
        List<AppUserResponse> actual = underTest.getAppUsers();
        // then
        List<AppUserResponse> expected = List.of(
                new AppUserResponse(user1.getId(), user1.getName()),
                new AppUserResponse(user2.getId(), user2.getName())
        );
        assertThat(actual).isEqualTo(expected);
        verify(appUserRepository).findAll();
    }

    @Test
    void getAppUsersEmpty() {
        when(appUserRepository.findAll()).thenReturn(List.of());
        assertThatThrownBy(() -> underTest.getAppUsers())
                .isInstanceOf(NoUsersFoundException.class)
                .hasMessageContaining("no users found");
    }
}