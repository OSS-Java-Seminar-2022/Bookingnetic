
package com.project.bookingnetic.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@ToString
@NoArgsConstructor
@Table(name = "accommodation")
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "accommodation_id")
    private long id;

    @Column(name = "accommodation_name")
    private String accommodationName;

    @Column(name = "description")
    private String description;

    @Column(name = "price_for_night")
    private BigDecimal priceForNight;

    @Column(name = "creation_date")
    private Date creationDate;


    @OneToOne
    @JoinColumn(name = "address_id", referencedColumnName = "address_id", insertable=false, updatable=false)
    private Address addressId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable=false, updatable=false)
    private User userId;

    public Accommodation(String accommodationName, String description, BigDecimal priceForNight, Date creationDate, Address addressId, User userId) {
        this.accommodationName = accommodationName;
        this.description = description;
        this.priceForNight = priceForNight;
        this.creationDate = creationDate;
        this.addressId = addressId;
        this.userId = userId;
    }
}

