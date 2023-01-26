package com.project.bookingnetic.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DTOAccommodation {

    private String title;
    private String description;
    private BigDecimal price;

    private String country;
    private String city;
    private String postalCode;
    private String street;

}

