package com.project.bookingnetic.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@ToString
@NoArgsConstructor
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    private Date registrationDate;

    @Column(name = "enum_role")
    private RoleType enumRole;
    @OneToMany(mappedBy = "account_fk")
    Set<Reservation> reservations;


    public User(String firstName, String lastName, String email, String password, String phone, String about, Date registrationDate, RoleType enumRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.registrationDate = registrationDate;
        this.enumRole = enumRole;
    }
}
