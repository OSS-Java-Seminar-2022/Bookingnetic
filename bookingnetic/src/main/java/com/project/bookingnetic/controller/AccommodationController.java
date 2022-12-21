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
    private AccommodationService accommodationService;

    public AccommodationController(AccommodationService accommodationService) {
        this.accommodationService = accommodationService;
    }

    @GetMapping
    public ResponseEntity<List<Accommodation>> getAccommodation(){
        return ResponseEntity.ok(accommodationService.getAccommodations());
    }

    @PostMapping
    public ResponseEntity<Accommodation> saveAccommodation(Accommodation address){
        return ResponseEntity.ok(accommodationService.saveAccommodation(address));
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id  ){
        return accommodationService.deleteById(id);
    }
}
