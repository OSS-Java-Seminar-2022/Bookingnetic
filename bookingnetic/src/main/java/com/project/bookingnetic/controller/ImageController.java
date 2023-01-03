package com.project.bookingnetic.controller;

import com.project.bookingnetic.models.Image;
import com.project.bookingnetic.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/image")
public class ImageController {
    @Autowired
    private final ImageService service;

    public ImageController(ImageService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Image>> get(){
        return ResponseEntity.ok(service.get());
    }

    @PostMapping
    public ResponseEntity<Image> save(Image image){
        return ResponseEntity.ok(service.save(image));
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id  ){
        return service.deleteById(id);
    }
}
