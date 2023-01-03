package com.project.bookingnetic.controller;


import com.project.bookingnetic.models.User;
import com.project.bookingnetic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping()
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok(service.getUsers());
    }


//    @GetMapping("/user/{id}")
//    public ResponseEntity<List<User>> getUserById(){
//        return "user/profile";
//    }

    @PostMapping("/create")
    public ResponseEntity<User> saveUser(@ModelAttribute("user") User user){

        return ResponseEntity.ok(service.saveUser(user));
    }
//    @PostMapping("/login")
//    public ResponseEntity<User> saveUser(@ModelAttribute("user") User user){
//
//        return ResponseEntity.ok(service.saveUser(user));
//    }
}
