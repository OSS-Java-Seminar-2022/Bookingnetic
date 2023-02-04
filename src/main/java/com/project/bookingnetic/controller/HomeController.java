package com.project.bookingnetic.controller;

import com.project.bookingnetic.models.User;
import com.project.bookingnetic.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(HttpSession session) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email;
        ModelAndView mav = new ModelAndView();
        if (principal instanceof UserDetails) {
            email = ((UserDetails)principal).getUsername();
        } else {
            email = principal.toString();
        }

        Optional<User> user = userService.findByEmail(email);

        user.ifPresent(data -> {
            final long user_id = data.getId();
            final String firstName = data.getFirstName();
            final String lastName = data.getLastName();
            final String mail = data.getEmail();
            final String phoneNumber = data.getPhone();

            session.setAttribute("user_id",user_id);
            session.setAttribute("firstName",firstName);
            session.setAttribute("lastName",lastName);
            session.setAttribute("email",mail);
            session.setAttribute("phone",phoneNumber);
        });
        return "home";
    }

    @GetMapping("/login")
    public String loginLink(){
        return "login";
    }

}