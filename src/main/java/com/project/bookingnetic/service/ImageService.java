package com.project.bookingnetic.service;


import com.project.bookingnetic.models.Accommodation;
import com.project.bookingnetic.models.Image;
import com.project.bookingnetic.repository.AccommodationRepository;
import com.project.bookingnetic.repository.ImageRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.Long.valueOf;

@Service
public class ImageService {
    @Autowired
    private ImageRepository repository;

    @Autowired
    private AccommodationRepository accommodationRepository;
    public List<Image> get(){
        return new ArrayList<>(repository.findAll());
    }

    public Image save(Image img){
        return repository.save(img);
    }

    public Optional<Image> getImageById(Long id){return repository.findById(id);}

    public void upload(MultipartFile file) throws IOException {
        Image fileEntity = new Image();
        fileEntity.setDescription(StringUtils.cleanPath(file.getOriginalFilename()));
        fileEntity.setImg(file.getBytes());
        Accommodation accommodation = accommodationRepository.findById(valueOf(2) ).get();
        fileEntity.setAccommodation(accommodation);
       // System.out.println(fileEntity);
        repository.save(fileEntity);
    }

    public ResponseEntity<HttpStatus> deleteById(Long id) {
        try {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Image showProductImage(Long id) {

        Image img = repository.findById(id).get();
        return img;

    }


}
