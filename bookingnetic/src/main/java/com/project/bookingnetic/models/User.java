package com.project.bookingnetic.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "c_user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "c_user_id")
    private long id;
    @Column(name = "c_first_name")
    private String FirstName;
    @Column(name = "c_last_name")
    private String LastName;
    @Column(name = "c_email", unique = true)
    private String email;
    @Column(name = "c_password")
    private String password;
    @Column(name = "c_phone")
    private String phone;
    @Column(name = "c_about")
    private String about;
//    @Column(name = "c_about", nullable = false)
//    private String Role;
}
