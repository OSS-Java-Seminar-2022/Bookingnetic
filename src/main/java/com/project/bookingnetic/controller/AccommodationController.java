package com.project.bookingnetic.controller;

import com.project.bookingnetic.models.Accommodation;
import com.project.bookingnetic.models.Search;
import com.project.bookingnetic.service.AccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/accommodation")
public class AccommodationController {
    @Autowired
    private AccommodationService service;


    private ReservationService reservationService;

    public AccommodationController(AccommodationService service,ReservationService reservationService) {
        this.service = service;
        this.reservationService = reservationService;
    }

    @GetMapping()
    public ResponseEntity<List<Accommodation>> get(){
        return ResponseEntity.ok(service.get());
    }


    @GetMapping("/findAvailableBySearch")
    public ResponseEntity<List<Accommodation>> findAvailableBySearch(@ModelAttribute(name = "search") Search search){
        Search userSearch = search;
        List<Accommodation> accommodationList = service.findByCity(userSearch.getCity());
        return ResponseEntity.ok(reservationService.findAvailable(userSearch, accommodationList));
    }

    @PostMapping
    public ResponseEntity<Accommodation> save(@RequestBody Accommodation accommodation){
        return ResponseEntity.ok(service.save(accommodation));
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id  ){
        return service.deleteById(id);
    }


}
