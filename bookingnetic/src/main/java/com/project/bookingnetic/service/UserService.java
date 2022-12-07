package com.project.bookingnetic.service;

import com.project.bookingnetic.models.User;
import com.project.bookingnetic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers(){
        return new ArrayList<>(userRepository.findAll());
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

}
