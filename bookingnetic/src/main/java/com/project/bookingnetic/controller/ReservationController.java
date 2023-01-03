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
    private final ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> get(){
        return ResponseEntity.ok(service.get());
    }

    @PostMapping
    public ResponseEntity<Reservation> save(Reservation reservation){
        return ResponseEntity.ok(service.save(reservation));
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id  ){
        return service.deleteById(id);
    }
}
