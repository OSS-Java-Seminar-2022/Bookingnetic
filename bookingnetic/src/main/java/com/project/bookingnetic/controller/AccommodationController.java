package com.project.bookingnetic.controller;

import com.project.bookingnetic.models.Accommodation;
import com.project.bookingnetic.service.AccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/accommodation")
public class AccommodationController {
    @Autowired
    private AccommodationService accommodationService;

    @GetMapping
    public ResponseEntity<List<Accommodation>> getAccommodation(){
        return ResponseEntity.ok(accommodationService.getAccommodations());
    }

    @PostMapping
    public ResponseEntity<Accommodation> saveAccommodation(Accommodation address){
        return ResponseEntity.ok(accommodationService.saveAccommodation(address));
    }
}
