package com.car_booking.carbooking;

import com.car_booking.carbooking.car.Brand;
import com.car_booking.carbooking.car.Car;
import com.car_booking.carbooking.entity.user.AppUser;
import com.car_booking.carbooking.repository.AppUserRepository;
import com.car_booking.carbooking.repository.CarRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
public class CarbookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarbookingApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(AppUserRepository appUserRepository,
										CarRepository carRepository) {
		return args -> {
			AppUser james = new AppUser("James");
			AppUser jamila = new AppUser("Jamila");
			appUserRepository.saveAll(List.of(james, jamila));
			Car tesla = new Car("TE-001", new BigDecimal("29.99"), Brand.TESLA, true);
			Car audi = new Car("AU-002", new BigDecimal("24.99"), Brand.AUDI, false);
			Car mercedes = new Car("ME-003", new BigDecimal("34.99"), Brand.MERCEDES, false);
			Car toyota = new Car("TO-004", new BigDecimal("19.99"), Brand.TOYOTA, false);
			Car teslaX = new Car("TE-005", new BigDecimal("39.99"), Brand.TESLA, true);
			carRepository.saveAll(List.of(tesla, audi, mercedes, toyota, teslaX));
		};
	}


}
