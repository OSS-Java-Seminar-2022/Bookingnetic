package com.project.bookingnetic.controller;



import com.project.bookingnetic.models.Accommodation;
import com.project.bookingnetic.models.Address;
import com.project.bookingnetic.models.RoleType;
import com.project.bookingnetic.models.User;
//import com.project.bookingnetic.security.UserSecurity;
import com.project.bookingnetic.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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



    @GetMapping("/register")
    public String renderRegisterPage(){
        return "register";
}
    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user){

        return service.register(user) ? "redirect: /login": "notSuccess" ;

    }
    @GetMapping("/{id}/accommodation")
    public ModelAndView renderUserAccommodation(ModelAndView mav, HttpSession session){



        mav.addObject("accommodation",new Accommodation());
        mav.addObject("address", new Address());
        mav.setViewName("createAccommodation");

        return mav;
    }
    @PostMapping("/{id}/accommodation")
    public String postUserAccommodation(@ModelAttribute Model model, Accommodation accommodation, HttpSession session){

        return "account";
    }
    @PostMapping("/login")
    public String loginPage(@ModelAttribute("user") User user, HttpSession session){

        if (service.userExistsByEmail(user.getEmail()))
        {
            return "redirect: /";
        }
        return "notSuccess";
    }
    @GetMapping("/{id}")
    public ModelAndView showAccount(Model model,HttpSession session, @PathVariable long id){

        return service.showAccount(id);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id  ){
        return service.deleteById(id);
    }

}
