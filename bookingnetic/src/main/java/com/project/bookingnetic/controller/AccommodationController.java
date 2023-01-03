package com.project.bookingnetic.controller;

import com.project.bookingnetic.models.Accommodation;
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
    private final AccommodationService service;

    public AccommodationController(AccommodationService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Accommodation>> getAccommodation(){
        return ResponseEntity.ok(service.getAccommodations());
    }

    @PostMapping
    public ResponseEntity<Accommodation> saveAccommodation(Accommodation address){
        return ResponseEntity.ok(service.saveAccommodation(address));
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id  ){
        return service.deleteById(id);
    }
}
