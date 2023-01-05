package com.project.bookingnetic.service;

import com.project.bookingnetic.models.User;
import com.project.bookingnetic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

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


}
