package com.project.bookingnetic.controller;



import com.project.bookingnetic.exception.MyException;
import com.project.bookingnetic.models.*;
//import com.project.bookingnetic.security.UserSecurity;
import com.project.bookingnetic.service.AccommodationService;
import com.project.bookingnetic.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

<<<<<<< HEAD
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
=======
>>>>>>> d5ee72b (update)
import java.util.ArrayList;
import java.util.List;
<<<<<<< HEAD
import java.util.Optional;
import java.util.stream.Stream;
=======
>>>>>>> d5ee72b (update)

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
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model){
        model.addAttribute("user", service.findById(id));
        return "edit-user";
    }
    @PostMapping("/update/{user_id}")
    public String editUpdate(@PathVariable Long user_id, @ModelAttribute("user") User user){

        service.update(user, user_id);
        return "redirect:/user/"+ user_id;
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id  ){
        return service.deleteById(id);
    }

    @GetMapping("/adminPage")
    public String showAdminPage(Model model){
        List<User> allUsers = service.get();
        List<AccommUserPair> pairList = new ArrayList<>();
        allUsers.forEach(user -> {
            List<Accommodation> accommodations = accommodationService.findByUser(user.getId());
            pairList.add(new AccommUserPair(accommodations, user));
        });
        model.addAttribute("pairList", pairList);
        return "admin-page";
    }
<<<<<<< HEAD
    public boolean checkEmailTaken(User user) {
        List<User> allUsers = service.get();
        List<String> emailList = allUsers.stream().map(u -> u.getEmail()).toList();
        return emailList.contains(user.getEmail());
    }
    @GetMapping("/addNewUser")
    public String adminAddUser(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "adminAddUser";
    }
    @PostMapping("/addNewUser")
    public String adminAddUser(@ModelAttribute("user") User user){
        if(checkEmailTaken(user)){
            return "bad-credentials";
        }
        service.hashAndSaveUser(user);
        return "redirect:/user/adminPage";
    }
=======

    @GetMapping("/adminPage/user-details/{id}")
    public String showUserDetails(Model model, @PathVariable("id") Long id){
        User user = service.findById(id);
        List<Accommodation> accommodations = accommodationService.findByUser(id);
        AccommUserPair accommUserPair= new AccommUserPair(accommodations, user);
        model.addAttribute("accommUserPair", accommUserPair);
        return "admin-view-user";



        //List<Accommodation>  allAccommodations = accommodationService.get();
        //model.addAttribute("allUsers", allUsers);
        //model.addAttribute("allAccommodations", allAccommodations);*/
    }

>>>>>>> d5ee72b (update)
    @GetMapping(path = "/delete/{acc_id}/{user_id}")
    public String Accommodation(@PathVariable("acc_id") Long acc_id, @PathVariable("user_id") Long user_id){
        accommodationService.deleteById(acc_id);
        return "redirect:/user/" + user_id;
    }

    @GetMapping(path = "/delete/{id}")
    public String adminDeleteUser(@PathVariable("id") Long imgId){
        service.deleteById(imgId);
        return "redirect:/user/adminPage";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model){
        model.addAttribute("user", service.findById(id));
        return "admin-edit-user";
    }

    @PostMapping("/update/{id}")
    public String editUpdate(@PathVariable Long id, @ModelAttribute("user") User user){
        service.hashAndSaveUser(user);
        user.setEnumRole(service.findById(id).getEnumRole());
        user.setRegistrationDate(service.findById(id).getRegistrationDate());
        service.update(user, id);
        return "redirect:/user/adminPage/user-details/"+ id;
    }

    @GetMapping("/view/accommodation/{id}")
    public String adminViewAccommodation(@PathVariable Long id, Model model){
        accommodationService.adminViewAcc(id, model);
        return "admin-accommodation";
    }

}
