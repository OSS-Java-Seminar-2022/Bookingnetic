package com.project.bookingnetic.service;

import com.project.bookingnetic.models.*;
import com.project.bookingnetic.repository.ImageRepository;
import com.project.bookingnetic.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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

    public ResponseEntity<HttpStatus> deleteById(Long id) {
        try {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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

}

