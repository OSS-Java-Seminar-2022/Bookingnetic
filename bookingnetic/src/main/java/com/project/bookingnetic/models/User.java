package com.project.bookingnetic.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "registration_date" )
    private LocalDate registrationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "enum_role")
    private RoleType enumRole;

<<<<<<< HEAD
=======

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;


    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Reservation> reservations = new HashSet<>();
>>>>>>> cee4d5658506c9be4e923a7fb489ee2f90fa3e33

    public User(String firstName,
                String lastName,
                String email,
                String password,
                String phone,
                RoleType enumRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.enumRole = enumRole;
    }


    /* setting username for authentication to use email */
    public String getUsername() {
        return this.email;
    }


    /*  finding all roles that user has */
    public List<String> getRoleList(){
        if(this.enumRole.toString().length() > 0)
            return Arrays.asList(this.enumRole.toString().split(","));

        return new ArrayList<>();
    }



}
