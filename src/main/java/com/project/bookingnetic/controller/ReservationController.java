package com.project.bookingnetic.controller;

import ch.qos.logback.core.model.Model;
import com.project.bookingnetic.exception.MyException;
import com.project.bookingnetic.models.CreateReservation;
import com.project.bookingnetic.models.Reservation;
import com.project.bookingnetic.service.ReservationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
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
    @GetMapping("/find-available")
    public ResponseEntity<String> findAvailable(
            @RequestParam String cityName,
            @RequestParam LocalDate dateFrom,
            @RequestParam LocalDate dateTo){
        return new ResponseEntity<>(cityName,HttpStatus.OK);
    }

    @PostMapping("/create")
    public String create(@ModelAttribute CreateReservation createReservation, HttpSession session){
        Object accomId = session.getAttribute("accom_id");
        Object userId = session.getAttribute("user_id");

        long accom = Long.valueOf(accomId.toString());
        long user = Long.valueOf(userId.toString());


        return  service.createReservation(user,accom,createReservation);

    }
    @GetMapping("/{id}")
    public ModelAndView create(@PathVariable long id){

        return service.renderReservation(id);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<Reservation> update(@PathVariable Long id, @RequestBody Reservation reservation) throws MyException {
        var update = service.update(reservation, id);
        if(update != null) {
            return ResponseEntity.ok(update);
        }
        throw new MyException("Reservation Not found");
    }

    @PostMapping(path = "/delete/{id}")
    public String deleteById(@PathVariable("id") Long id  ){
        return service.deleteById(id);
    }
}
