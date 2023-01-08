package com.project.bookingnetic.controller;



import com.project.bookingnetic.models.RoleType;
import com.project.bookingnetic.models.User;
import com.project.bookingnetic.security.UserSecurity;
import com.project.bookingnetic.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    public ResponseEntity<User> register(@ModelAttribute("user") User user){
        return ResponseEntity.ok(service.register(user));
    }

    /*@GetMapping("/login")
    public String loginLink(){
        return "login";
    }
*/
    @PostMapping("/login")
    public String loginPage(@ModelAttribute("user") User user){
        if (service.findByEmail(user.getEmail()))
            return "account";
        return "notSuccess";


    }
    @GetMapping("/account")
    public String showAccount(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        model.addAttribute("username",username );
        System.out.println("E MAIL AUTENTICIRANOG USERA JE : " +  username);
        return "account";


    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id  ){
        return service.deleteById(id);
    }

}
