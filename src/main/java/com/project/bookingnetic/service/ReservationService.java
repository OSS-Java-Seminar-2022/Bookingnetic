package com.project.bookingnetic.service;

import com.project.bookingnetic.models.Accommodation;
import com.project.bookingnetic.models.Reservation;
import com.project.bookingnetic.models.Search;
import com.project.bookingnetic.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class ReservationService {
    @Autowired
    private ReservationRepository repository;


    public List<Reservation> get(){
        return new ArrayList<>(repository.findAll());
    }

    public Reservation save(Reservation reservation) {
        return repository.save(reservation);
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

    public List<Accommodation> findAvailable(Search search, List<Accommodation> accommodationList){
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
        return finalList;
    }

}

