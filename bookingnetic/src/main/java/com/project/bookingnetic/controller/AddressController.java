package com.project.bookingnetic.controller;

import com.project.bookingnetic.models.Address;
import com.project.bookingnetic.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping
    public ResponseEntity<List<Address>> getUsers(){
        return ResponseEntity.ok(addressService.getAddresses());
    }

    @PostMapping
    public ResponseEntity<Address> saveUser(Address address){
        return ResponseEntity.ok(addressService.saveAddress(address));
    }
}
