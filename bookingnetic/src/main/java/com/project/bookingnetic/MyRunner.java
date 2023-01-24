package com.project.bookingnetic;

import com.project.bookingnetic.models.*;
import com.project.bookingnetic.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(MyRunner.class);


    private final UserService userService;
    private final AddressService addressService;
    private final AccommodationService accommodationService;

    private final ImageService imageService;
    private final ReservationService reservationService;

    public MyRunner( UserService userService, AddressService addressService, AccommodationService accommodationService,
                     ImageService imageService, ReservationService reservationService) {
        this.userService = userService;
        this.addressService = addressService;
        this.accommodationService = accommodationService;
        this.imageService = imageService;
        this.reservationService = reservationService;
    }

    @Override
    public void run(String... args) throws Exception {

        AppConfig appConfig = new AppConfig();
        appConfig.deserializeAddress("jsonFiles/addresses.json");
        appConfig.getAddress().forEach(address -> {
            addressService.save(address);
        });

        appConfig.deserializeAccomm("jsonFiles/accommodations.json");

        appConfig.getAccommodation().forEach(acc -> {
            long addrId = acc.getAddress().getId();
            Address address = addressService.findById(addrId);
            acc.setAddress(address);
            accommodationService.save(acc);
        });

        appConfig.deserializeUser("jsonFiles/users.json");

        appConfig.getUsers().forEach(user -> {
            long accId = user.getAccommodation().getId();
            user.setAccommodation(accommodationService.findById(accId));
            System.out.println(user);
            userService.hashUser(user);
        });

         appConfig.deserializeImage("jsonFiles/images.json");

        appConfig.getImages().forEach(img -> {
            long accommodationId = img.getAccommodation().getId();
            Accommodation accommodation = accommodationService.findById(accommodationId);
            img.setAccommodation(accommodation);
            System.out.println(img);
            imageService.save(img);
        });

    }
}

