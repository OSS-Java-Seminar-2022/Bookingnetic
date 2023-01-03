package com.project.bookingnetic.service;


import com.project.bookingnetic.models.Image;
import com.project.bookingnetic.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {
    @Autowired
    private ImageRepository repository;

    public List<Image> get(){
        return new ArrayList<>(repository.findAll());
    }

    public Image save(Image image) {
        return repository.save(image);
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
