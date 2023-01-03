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
    private AccommodationRepository repository;


    public List<Accommodation> get(){
        return new ArrayList<>(repository.findAll());
    }

    public Accommodation save(Accommodation accommodation) {
        return repository.save(accommodation);
    }

    public ResponseEntity<HttpStatus> deleteById(Long id) {
        try {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
