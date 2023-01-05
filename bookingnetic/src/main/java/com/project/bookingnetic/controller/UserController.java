package com.project.bookingnetic.controller;



import com.project.bookingnetic.models.RoleType;
import com.project.bookingnetic.models.User;
import com.project.bookingnetic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<List<User>> get(){
        return ResponseEntity.ok(service.get());
    }


//    @GetMapping("/user/{id}")
//    public ResponseEntity<List<User>> getUserById(){
//        return "user/profile";
//    }
    @GetMapping("/register")
    public String renderRegisterPage(){
        return "register";
}
    @PostMapping("/register")
    public ResponseEntity<User> save(@ModelAttribute("user") User user){

        user.setEnumRole(RoleType.USER);
        user.setRegistrationDate(LocalDate.now());
        return ResponseEntity.ok(service.save(user));
    }

    @PostMapping("/account")
    public String showAccount(@ModelAttribute("user") User user){
        if (service.findByEmail(user.getEmail()))
            return "success";
        return "notSuccess";


    }
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id  ){
        return service.deleteById(id);
    }

}
