package com.project.bookingnetic.controller;

import com.project.bookingnetic.models.Image;
import com.project.bookingnetic.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/image")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @GetMapping
    public ResponseEntity<List<Image>> getAddress(){
        return ResponseEntity.ok(imageService.getImages());
    }

    @PostMapping
    public ResponseEntity<Image> saveAddress(Image address){
        return ResponseEntity.ok(imageService.saveImage(address));
    }
}
