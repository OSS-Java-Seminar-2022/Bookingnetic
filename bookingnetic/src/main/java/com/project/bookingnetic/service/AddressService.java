package com.project.bookingnetic.service;

import com.project.bookingnetic.models.Address;
import com.project.bookingnetic.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    public List<Address> getAddresses(){
        return new ArrayList<>(addressRepository.findAll());
    }

    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    public ResponseEntity<HttpStatus> deleteById(Long id) {
        try {
            addressRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
