package com.project.bookingnetic.service;

import com.project.bookingnetic.models.Reservation;
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

    public ResponseEntity<String> findAvailable(@RequestParam String cityName, @RequestParam LocalDate dateFrom, @RequestParam LocalDate dateTo){
        return new ResponseEntity<>(cityName,HttpStatus.OK);
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
