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

//        appConfig.deserializeAddress("jsonFiles/addresses.json");
//        appConfig.getAddress().forEach(addressService::save);
//
//        appConfig.deserializeUser("jsonFiles/users.json");
//        appConfig.getUsers().forEach(userService::hashAndSaveUser);

        appConfig.deserializeAccomm("jsonFiles/accommodations1.json");
        appConfig.getAccommodation().forEach(acc -> {
            Address address = getNewAddress(acc.getAddress());
            User usr = getNewUser(acc.getUser());
            usr.hashPassword();
            Accommodation accommodation = new Accommodation(acc.getTitle(), acc.getDescription(),
                    acc.getPrice_for_night(), address,usr);
            accommodationService.save(accommodation);
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
//

    }

    public Accommodation getAccommodation(long accID){
        return accommodationService.findById(accID);
    }
    public User getUser(long usrID){
        return userService.findById(usrID);
    }
    public User getNewUser(User user){
        return new User(user.getFirstName(), user.getLastName(), user.getEmail(),
                        user.getPassword(), user.getPhone(), user.getEnumRole());
    }

    public Address getNewAddress(Address address){
        return new Address(address.getCountry(), address.getCity(), address.getStreet(), address.getPostalCode());
    }
}

