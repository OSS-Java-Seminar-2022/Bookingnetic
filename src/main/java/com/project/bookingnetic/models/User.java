package com.project.bookingnetic.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

    public User(String firstName,
                String lastName,
                String email,
                String password,
                String phone,
                RoleType enumRole) {

        setParameters(firstName, lastName, email, password, phone, enumRole);
    }

    public void setParameters(String firstName, String lastName, String email,
                         String password, String phone, RoleType enumRole){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.enumRole = enumRole;
    }


    /* setting username for authentication to use email, by default it is username, but we want email */
    public String getUsername() {
        return this.email;
    }

    public String getFullName(){
        return this.firstName + " " + this.lastName;

    }
    /*  finding all roles that user has */
    public List<String> getRoleList(){
        if(this.enumRole.toString().length() > 0)
            return Arrays.asList(this.enumRole.toString().split(","));
        return new ArrayList<>();
    }

    public void hashPassword(){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(this.password);
    }


    public void updateUser(String firstName, String lastName, String email, String phone) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }
}
