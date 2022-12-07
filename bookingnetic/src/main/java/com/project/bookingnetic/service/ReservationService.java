package com.project.bookingnetic.service;

import com.project.bookingnetic.models.Reservation;
import com.project.bookingnetic.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;


    public List<Reservation> getReservations(){
        return new ArrayList<>(reservationRepository.findAll());
    }

    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }
}
