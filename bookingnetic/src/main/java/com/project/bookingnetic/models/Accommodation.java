
package com.project.bookingnetic.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "c_accommodation")
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "c_accommodation_id")
    private long id;
    @Column(name = "c_name")
    private String name;
    @Column(name = "c_description")
    private String description;
    @OneToOne
    @JoinColumn(name = "c_address_fk", referencedColumnName = "c_address_id", insertable=false, updatable=false)
    private Address address;
    @OneToOne
    @JoinColumn(name = "c_user_fk", referencedColumnName = "c_user_id", insertable=false, updatable=false)
    private User userID;
    @Column(name = "c_price")
    private BigDecimal price;
}

