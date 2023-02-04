package com.project.bookingnetic.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "postal_code")
    private String postalCode;

    public Address(String country, String city, String street, String postalCode) {
        setParameters(country, city, street, postalCode);
    }

    public void setParameters(String country, String city, String street, String postalCode){
        this.country = country;
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
    }
}
