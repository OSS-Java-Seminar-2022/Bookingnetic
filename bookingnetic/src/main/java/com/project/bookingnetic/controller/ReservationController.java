package com.project.bookingnetic.controller;

import com.project.bookingnetic.models.Reservation;
import com.project.bookingnetic.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reservation")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getReservation(){
        return ResponseEntity.ok(reservationService.getReservations());
    }

    @PostMapping
    public ResponseEntity<Reservation> saveReservation(Reservation address){
        return ResponseEntity.ok(reservationService.saveReservation(address));
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id  ){
        return reservationService.deleteById(id);
    }
}
