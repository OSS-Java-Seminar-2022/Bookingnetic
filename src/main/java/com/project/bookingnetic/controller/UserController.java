package com.project.bookingnetic.controller;



import com.project.bookingnetic.exception.MyException;
import com.project.bookingnetic.models.Accommodation;
import com.project.bookingnetic.models.Address;
import com.project.bookingnetic.models.RoleType;
import com.project.bookingnetic.models.User;
//import com.project.bookingnetic.security.UserSecurity;
import com.project.bookingnetic.service.AccommodationService;
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

    @Autowired
    private AccommodationService accommodationService;

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
        return service.register(user) ? "redirect:/login": "404";
    }
    @GetMapping("/{user_id}/accommodation")
    public String renderUserAccommodation(){
        return "create-accommodation";
    }
    @PostMapping("/{user_id}/accommodation")
    public String postUserAccommodation(@ModelAttribute("accommodation") Accommodation accommodation, @PathVariable(name="user_id") long id){
        Accommodation acc = accommodation;
        return  service.addAccommodationToUser(acc,id);
    }



    @GetMapping("/{user_id}")
    public ModelAndView showAccount(Model model,HttpSession session, @PathVariable(name = "user_id") long id){

        return service.showAccount(id);
    }
    @PutMapping("/put/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) throws MyException {
        var update = service.update(user, id);
        if(update != null){
            return ResponseEntity.ok(user);
        }
        throw new MyException("User Not found");
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id  ){
        return service.deleteById(id);
    }


    @GetMapping("/adminPage")
    public String showAdminPage(Model model){
        List<User> allUsers = service.get();
        List<Accommodation>  allAccommodations = accommodationService.get();
        model.addAttribute("allUsers", allUsers);
        model.addAttribute("allAccommodations", allAccommodations);
        return "admin-page";
    }

}
