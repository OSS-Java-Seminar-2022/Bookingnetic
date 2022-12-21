package com.project.bookingnetic.service;

import com.project.bookingnetic.models.Accommodation;
import com.project.bookingnetic.repository.AccommodationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccommodationService {
    @Autowired
    private AccommodationRepository accommodationRepository;


    public List<Accommodation> getAccommodations(){
        return new ArrayList<>(accommodationRepository.findAll());
    }

    public Accommodation saveAccommodation(Accommodation accommodation) {
        return accommodationRepository.save(accommodation);
    }

    public ResponseEntity<HttpStatus> deleteById(Long id) {
        try {
            accommodationRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
