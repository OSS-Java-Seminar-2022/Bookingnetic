package com.project.bookingnetic.service;

import com.project.bookingnetic.models.*;
import com.project.bookingnetic.repository.AccommodationRepository;
import com.project.bookingnetic.repository.ImageRepository;
import com.project.bookingnetic.repository.ReservationRepository;
import com.project.bookingnetic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.Email;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;


@Service
public class ReservationService {
    @Autowired
    private ReservationRepository repository;
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;

    @Autowired
    private AccommodationRepository accommodationRepository;

    public ReservationService(ReservationRepository repository, ImageRepository imageRepository) {
        this.repository = repository;
        this.imageRepository = imageRepository;
    }

    public List<Reservation> get(){
        return new ArrayList<>(repository.findAll());
    }

    public Reservation save(Reservation reservation) {
        return repository.save(reservation);
    }

    public Reservation update(Reservation reservation, Long id) {
        if(repository.findById(id).isPresent()){
            var update = repository.findById(id).get();
            update.setParameters(reservation.getReservation_date(), reservation.getCheckIn(), reservation.getCheckOut(),
                                    reservation.getPrice(), reservation.getUser(), reservation.getAccommodation());
            return repository.save(update);
        }
        return null;
    }

    public String deleteById(Long id) {
        try {
            repository.deleteById(id);
            return "redirect:/";
        } catch (Exception e){
            return "404";
        }
    }

    public List<Reservation> findReservationsByAccommodationId(Long id){
        return repository.findAllByAccommodation_Id(id);
    }

    public Optional<Image> returnAccommImage(Accommodation accommodation){
        return Optional.ofNullable(imageRepository.findFirstByAccommodation_Id(accommodation.getId()));
    }

    /*public ModelAndView findAvailable(Search search, List<Accommodation> accommodationList){
        ModelAndView mav = new ModelAndView();
        LocalDate userCheckIn = search.getDateFrom();
        LocalDate userCheckOut = search.getDateTo();
        Boolean accommodationAvailable = true;
        List<Accommodation> finalList = new ArrayList<>();


        for(int i = 0; i < accommodationList.size();++i) {
            var a = accommodationList.get(i);
            List<Reservation> reservations = findReservationsByAccommodationId(a.getId());

            for(int j = 0; j < reservations.size();++j) {
                var r = reservations.get(j);
                LocalDate checkIn = r.getCheckIn();
                LocalDate checkOut = r.getCheckOut();

                if (userCheckIn.equals(checkIn) ||
                        userCheckIn.equals(checkOut) ||
                        userCheckOut.equals(checkIn) ||
                        userCheckOut.equals(checkOut) ||
                        userCheckIn.isBefore(checkIn) && userCheckOut.isAfter(checkOut) ||
                        userCheckIn.isBefore(checkIn) && userCheckOut.isAfter(checkIn) ||
                        userCheckIn.isBefore(checkOut) && userCheckOut.isAfter(checkOut) ||
                        userCheckIn.isAfter(checkIn) && userCheckOut.isBefore(checkOut)) {
                    accommodationAvailable = false;
                }

            }
            if (accommodationAvailable) {
                finalList.add(a);

            }
            else {
                accommodationAvailable = true;
            }
        }
        mav.addObject("accommodations",finalList);
        mav.setViewName("list-found-accommodations");
        return mav;
    }*/

    public ModelAndView findAvailable(Search search, List<Accommodation> accommodationList){
        ModelAndView mav = new ModelAndView();
        LocalDate userCheckIn = search.getDateFrom();
        LocalDate userCheckOut = search.getDateTo();
        if(search.getDateTo().isBefore(search.getDateFrom())) {
            mav.setViewName("error-wrong-dates");
            return mav;
        }
        Boolean accommodationAvailable = true;
        List<AccommImagePair> pairList = new ArrayList<>();
        for(int i = 0; i < accommodationList.size();++i) {
            var a = accommodationList.get(i);
            List<Reservation> reservations = findReservationsByAccommodationId(a.getId());
            for(int j = 0; j < reservations.size();++j) {
                var r = reservations.get(j);
                LocalDate checkIn = r.getCheckIn();
                LocalDate checkOut = r.getCheckOut();
                if (userCheckIn.equals(checkIn) ||
                        userCheckIn.equals(checkOut) ||
                        userCheckOut.equals(checkIn) ||
                        userCheckOut.equals(checkOut) ||
                        userCheckIn.isBefore(checkIn) && userCheckOut.isAfter(checkOut) ||
                        userCheckIn.isBefore(checkIn) && userCheckOut.isAfter(checkIn) ||
                        userCheckIn.isBefore(checkOut) && userCheckOut.isAfter(checkOut) ||
                        userCheckIn.isAfter(checkIn) && userCheckOut.isBefore(checkOut)) {
                    accommodationAvailable = false;
                }
            }
            if (accommodationAvailable) {
                Optional<Image> image = returnAccommImage(a);
                if(image.isPresent()){
                    var img = Base64.getEncoder().encodeToString(image.get().getImg());
                    pairList.add(new AccommImagePair(a, img));
                }
                else {
                    pairList.add(new AccommImagePair(a, null));
                }
            }
            else {
                accommodationAvailable = true;
            }
        }
        mav.addObject("pairList", pairList);
        mav.setViewName("list-found-accommodations");
        return mav;
    }

    public String createReservation(long userId, long accommodationId, CreateReservation createReservation) {
        Reservation reservation = new Reservation(LocalDate.now(),createReservation.getDateFrom(),createReservation.getDateTo(),createReservation.getTotalPrice());

        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Accommodation> accomOpt = accommodationRepository.findById(accommodationId);

        userOpt.ifPresent(user -> reservation.setUser(user));
        accomOpt.ifPresent(accommodation -> reservation.setAccommodation(accommodation));

        try{
            repository.save(reservation);
            String user = reservation.getUser().getFullName();
            String accommodation = reservation.getAccommodation().getTitle();
            String total = reservation.getPrice().toString();
            final String body = user + " reserved you accommodation "
                    + accommodation + " for " + total
                    + "$\n\n Check in date: " + reservation.getCheckIn()
                    +"\n\n Check out date: " + reservation.getCheckOut();
            emailService.sendEmail(
                        reservation.getAccommodation().getUser().getEmail(),
                        reservation.getAccommodation().getUser().getFullName(),
                        "You have a new reservation!",
                        body);
            return "redirect:/reservation/"+reservation.getId();
        }
        catch(Exception e){
            e.printStackTrace();
            return "500";
        }

    }

    public ModelAndView renderReservation(long id) {
        ModelAndView mav = new ModelAndView("404");

        Optional<Reservation> reservationOpt = repository.findById(id);

        reservationOpt.ifPresent(reservation -> {
            mav.addObject("reservation",reservation);
            mav.setViewName("render-reservation");
        });

        return mav;
    }
}

