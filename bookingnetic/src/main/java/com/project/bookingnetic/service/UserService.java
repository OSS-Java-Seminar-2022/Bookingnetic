package com.project.bookingnetic.service;

import com.project.bookingnetic.models.Accommodation;
import com.project.bookingnetic.models.RoleType;
import com.project.bookingnetic.models.User;
import com.project.bookingnetic.repository.AccommodationRepository;
import com.project.bookingnetic.repository.UserRepository;
import com.project.bookingnetic.security.UserSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserService implements UserDetailsService {


    @Autowired
    private UserRepository repo;
    private final AccommodationRepository accommodationRepository;

    public UserService(UserRepository repo,
                       AccommodationRepository accommodationRepository) {
        this.repo = repo;
        this.accommodationRepository = accommodationRepository;
    }



    public List<User> get(){
        return new ArrayList<>(repo.findAll());
    }

    public User save(User user){
        return repo.save(user);
    }

    public ResponseEntity<HttpStatus> deleteById(Long id) {
        try {
            repo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean userExistsByEmail(String email){
        Optional<User> user = Optional.ofNullable(repo.findByEmail(email));
       return user.isPresent() ? true : false;



    }

    public Optional<User> findByEmail(String email){
        Optional<User> user = Optional.ofNullable(repo.findByEmail(email));
        return user;
    }

    public ModelAndView showAccount(long id){

        ModelAndView mav = new ModelAndView();

        Optional<User> opt = repo.findById(id);

        opt.ifPresent(user -> {
            mav.addObject("user", user);
            mav.addObject("accommodation",user.getAccommodation());

        });
        mav.addObject("username",id );
        mav.setViewName("/account");

        return mav;
    }

    public boolean register(User user){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //hashPassword(user);
        //user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnumRole(RoleType.USER);
        user.setRegistrationDate(LocalDate.now());


        try{
            repo.save(user);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /*
    public void hashPassword(User user){
        var encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    public boolean checkPassword(User user, String password){
        return encoder.matches(password, user.getPassword());
    }
*/

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.repo.findByEmail(email);
        UserSecurity userSecurity = new UserSecurity(user);
        return userSecurity;
    }
}
