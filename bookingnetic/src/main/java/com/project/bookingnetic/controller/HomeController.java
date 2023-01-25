package com.project.bookingnetic.controller;

import com.project.bookingnetic.models.User;
import com.project.bookingnetic.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.Value;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
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
            session.setAttribute("user_id",user_id);
        });
        return "home";
    }

    @GetMapping("/login")
    public String loginLink(){
        return "login";
    }

}