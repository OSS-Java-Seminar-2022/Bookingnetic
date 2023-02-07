package com.project.bookingnetic.service;

import com.project.bookingnetic.models.Address;
import com.project.bookingnetic.models.User;
import com.project.bookingnetic.repository.AddressRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressService {
    @Autowired
    private AddressRepository repository;

    public List<Address> get(){
        return new ArrayList<>(repository.findAll());
    }

    public Address save(Address address){
        var city = address.getCity();
        address.setCity(StringUtils.capitalize(city));
        return repository.save(address);
    }

    public ResponseEntity<HttpStatus> deleteById(Long id) {
        try {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Address update(Address address, Long id) {
        if(repository.findById(id).isPresent()){
            var update = repository.findById(id).get();
            update.setParameters(address.getCountry(), address.getCity(),
                    address.getStreet(), address.getPostalCode());
            return repository.save(update);
        }
        return null;
    }

    public Address findById(Long id){
        return repository.findById(id).get();
    }
}
