package com.project.bookingnetic.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "c_address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "c_address_id")
    private long id;
    @Column(name = "c_country")
    private String country;
    @Column(name = "c_city")
    private String city;
    @Column(name = "c_street")
    private String street;
    @Column(name = "c_postal_code")
    private String postalCode;
}
