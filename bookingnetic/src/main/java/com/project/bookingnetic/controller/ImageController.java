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
    private ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping
    public ResponseEntity<List<Image>> getAddress(){
        return ResponseEntity.ok(imageService.getImages());
    }

    @PostMapping
    public ResponseEntity<Image> saveAddress(Image address){
        return ResponseEntity.ok(imageService.saveImage(address));
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id  ){
        return imageService.deleteById(id);
    }
}
