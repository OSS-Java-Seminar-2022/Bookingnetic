package com.project.bookingnetic.controller;

import com.project.bookingnetic.exception.MyException;
import com.project.bookingnetic.models.Accommodation;
import com.project.bookingnetic.models.Search;
import com.project.bookingnetic.service.AccommodationService;
import com.project.bookingnetic.service.ReservationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static java.util.concurrent.TimeUnit.DAYS;

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

    @GetMapping("/{id}")
    public ModelAndView getAccommodationById(@PathVariable(name = "id") long id){
        return service.getAccommodationById(id);
    }



    @GetMapping("/findAvailableBySearch")
    public ModelAndView findAvailableBySearch(@ModelAttribute(name = "search") Search search, HttpSession session){
        session.setAttribute("search",search);
        session.setAttribute("total_days", DAYS.toChronoUnit().between(search.getDateFrom(), search.getDateTo()));
        List<Accommodation> accommodationList = service.findByCity(search.getCity());
        return reservationService.findAvailable(search, accommodationList);
    }

    @PostMapping
    public ResponseEntity<Accommodation> save(@RequestBody Accommodation accommodation){
        return ResponseEntity.ok(service.save(accommodation));
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<Accommodation> update(@PathVariable Long id, @RequestBody Accommodation accommodation) throws MyException {
        var update = service.update(accommodation, id);
        if(update != null) {
            return ResponseEntity.ok(update);
        }
        throw new MyException("Accommodation Not found");
    }
    @PostMapping("/{accom_id}/image")
    public String uploadImage(Model model, @RequestParam("file") MultipartFile file, @PathVariable(name = "accom_id") long accom_id) {
        try {
             return service.addImageToAccommodation(file,accom_id);


        } catch (Exception e) {
            return "500";
        }
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id  ){
        return service.deleteById(id);
    }


}
