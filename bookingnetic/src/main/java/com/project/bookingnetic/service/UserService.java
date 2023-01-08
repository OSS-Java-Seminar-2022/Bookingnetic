package com.project.bookingnetic.service;

import com.project.bookingnetic.models.RoleType;
import com.project.bookingnetic.models.User;
import com.project.bookingnetic.repository.UserRepository;
import com.project.bookingnetic.security.UserSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    public List<User> get(){
        return new ArrayList<>(userRepository.findAll());
    }

    public User save(User user){
        return userRepository.save(user);
    }

    public ResponseEntity<HttpStatus> deleteById(Long id) {
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean findByEmail(String email){
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));
        if(user.isPresent())
            return true;
        return false;



    }

    public User register(User user){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //hashPassword(user);
        //user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnumRole(RoleType.USER);
        user.setRegistrationDate(LocalDate.now());
        return userRepository.save(user);
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
        User user = this.userRepository.findByEmail(email);
        UserSecurity userSecurity = new UserSecurity(user);
        return userSecurity;
    }
}
