package com.project.bookingnetic.service;


import com.project.bookingnetic.models.Image;
import com.project.bookingnetic.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    public List<Image> getImages(){
        return new ArrayList<>(imageRepository.findAll());
    }

    public Image saveImage(Image image) {
        return imageRepository.save(image);
    }


}
