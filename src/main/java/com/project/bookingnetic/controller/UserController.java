package com.project.bookingnetic.controller;



import com.project.bookingnetic.exception.MyException;
import com.project.bookingnetic.models.Accommodation;
import com.project.bookingnetic.models.Address;
import com.project.bookingnetic.models.User;
//import com.project.bookingnetic.security.UserSecurity;
import com.project.bookingnetic.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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



    @GetMapping("/register")
    public String renderRegisterPage(){
        return "register";
}
    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user){

        return service.register(user) ? "redirect:/login": "notSuccess" ;

    }
    @GetMapping("/{id}/accommodation")
    public ModelAndView renderUserAccommodation(ModelAndView mav, HttpSession session){



        mav.addObject("accommodation",new Accommodation());
        mav.addObject("address", new Address());
        mav.setViewName("createAccommodation");

        return mav;
    }
    @PostMapping("/{id}/accommodation")
    public String postUserAccommodation(@ModelAttribute Accommodation accommodation){

        return "account";
    }
    @PostMapping("/login")
    public String loginPage(@ModelAttribute("user") User user){

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

}
