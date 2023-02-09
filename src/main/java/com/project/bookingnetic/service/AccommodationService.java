package com.project.bookingnetic.service;

import com.project.bookingnetic.models.Accommodation;
import com.project.bookingnetic.models.Image;
import com.project.bookingnetic.models.RoleType;
import com.project.bookingnetic.models.User;
import com.project.bookingnetic.repository.AccommodationRepository;
import com.project.bookingnetic.repository.ImageRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.*;

@Service
public class AccommodationService {
    @Autowired
    private AccommodationRepository repository;

    @Autowired
    private ImageRepository imageRepository;


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

    public Accommodation update(Accommodation accommodation, Long id) {
        if(repository.findById(id).isPresent()){
            var update = repository.findById(id).get();
            update.setParameters(accommodation.getTitle(), accommodation.getDescription(),
                    accommodation.getPrice_for_night(), accommodation.getAddress(), accommodation.getUser());
            return repository.save(update);
        }
        return null;
    }
    public Accommodation findById(Long id){
        return repository.findById(id).get();
    }

    public Optional<Accommodation> getById(Long id){
        return repository.findById(id);
    }


    public List<Accommodation> findByCity(String city){
        return repository.findAllByAddress_City(StringUtils.capitalize(city.toLowerCase()));
    }

    public  List<Accommodation> findByUser(Long id){
        return repository.getAccommodationsByUserFk(id);

    }

    public ModelAndView renderAccommodationById(long id) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("404");

        Optional<Accommodation> accOpt =  repository.findById(id);
        setMavForAcc(accOpt, mav);
        return mav;
    }

    public void setMavForAcc(Optional<Accommodation> accOpt, ModelAndView mav){
        accOpt.ifPresent(accommodation -> {
            List<Image> images = imageRepository.findByAccommodationFk(accommodation.getId());
            HashMap<Integer,String> imagesMap = imagesMap(images);

            mav.setViewName("render-accommodation");
            mav.addObject("images",imagesMap);
            mav.addObject(accommodation);
        });
    }

    public HashMap<Integer, String> imagesMap(List<Image> images){
        HashMap<Integer,String> imgMap = new HashMap<Integer,String>();
        images.forEach(image -> {
            var img = Base64.getEncoder().encodeToString(image.getImg());
            imgMap.put((int)image.getId(), img);
        });
        return imgMap;
    }

    public void adminViewAcc(long id, Model model){
        Optional<Accommodation> accOpt =  repository.findById(id);

        accOpt.ifPresent(accommodation -> {
            List<Image> images = imageRepository.findByAccommodationFk(accommodation.getId());
            HashMap<Integer, String> imagesMap = imagesMap(images);

            model.addAttribute("images", imagesMap);
            model.addAttribute("accommodation", accommodation);
        });
    }

    public String addImageToAccommodation(MultipartFile file, long accom_id) throws IOException {
        Image fileEntity = new Image();
        fileEntity.setDescription(StringUtils.cleanPath(file.getOriginalFilename()));
        fileEntity.setImg(file.getBytes());

        Optional<Accommodation> accOpt = repository.findById(accom_id);
        accOpt.ifPresent(accommodation -> {
            fileEntity.setAccommodation(accommodation);
            imageRepository.save(fileEntity);

        });
        return "redirect:/accommodation/"+accom_id;

    }
}


