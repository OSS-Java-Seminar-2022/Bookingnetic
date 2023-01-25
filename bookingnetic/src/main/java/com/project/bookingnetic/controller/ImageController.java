package com.project.bookingnetic.controller;

import com.project.bookingnetic.models.Image;
import com.project.bookingnetic.models.Reservation;
import com.project.bookingnetic.service.ImageService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.compress.utils.IOUtils;
import org.bouncycastle.math.raw.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
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

    @GetMapping("/new")
    public String newImage(Model model) {
        return "upload_form";
    }
    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(Model model, @RequestParam("file") MultipartFile file) {
        try {
            service.upload(file);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("File uploaded successfully: %s", file.getOriginalFilename()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("Could not upload the file: %s!", file.getOriginalFilename()));
        }
    }

    @PostMapping()
    public ResponseEntity<Image> save(Image image) {
        return ResponseEntity.ok(service.save(image));
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id  ){
        return service.deleteById(id);
    }

    @GetMapping("/img")
    public String getHomePage(Model model) {
        //var img = Base64.getEncoder().encodeToString(service.showProductImage(id).getImg());
        //model.addAttribute("img", img);
        return "show_image";
    }
    @GetMapping("/img/{id}")
    public String showProductImage(Model model, @PathVariable Long id) throws IOException {
        var img = Base64.getEncoder().encodeToString(service.showProductImage(id).getImg());
        model.addAttribute("image", img);
        return "show_image";
    }
}
