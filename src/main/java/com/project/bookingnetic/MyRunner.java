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

        appConfig.deserializeUser("jsonFiles/users.json");
        appConfig.getUsers().forEach(user -> {
            userService.hashAndSaveUser(user);
        });


        appConfig.deserializeAccomm("jsonFiles/accommodations.json");
        appConfig.getAccommodation().forEach(acc -> {
            acc.setAddress(getAddress(acc.getAddress().getId()));
            acc.setUser(getUser(acc.getUser().getId()));
            accommodationService.save(acc);
        });


        appConfig.deserializeImage("jsonFiles/images.json");
        appConfig.getImages().forEach(img -> {
            img.setAccommodation(getAccommodation(img.getAccommodation().getId()));
            imageService.save(img);
        });


        appConfig.deserializeReservation("jsonFiles/reservations.json");
        System.out.println(appConfig.getReservations());
        appConfig.getReservations().forEach(res -> {
            res.setAccommodation(getAccommodation(res.getAccommodation().getId()));
            res.setUser(getUser(res.getUser().getId()));
            reservationService.save(res);
        });
    }

    public Accommodation getAccommodation(long accID){
        return accommodationService.findById(accID);
    }
    public User getUser(long usrID){
        return userService.findById(usrID);
    }

    public Address getAddress(long addrID){
        return addressService.findById(addrID);
    }
}

