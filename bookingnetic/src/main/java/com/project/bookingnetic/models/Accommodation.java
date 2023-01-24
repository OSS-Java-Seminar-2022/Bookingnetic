
package com.project.bookingnetic.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.thymeleaf.expression.Maps;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@ToString
@NoArgsConstructor
@Table(name = "accommodation")
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "price_for_night")
    private BigDecimal price_for_night;

    @Column(name = "creation_date" )
    private Date creation_date;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_fk", referencedColumnName = "id")
    private Address address;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "accommodation")
    private Set<Image> images = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "accommodation")
    private Set<Reservation> reservations = new HashSet<>();

    public Accommodation(String title,
                         String description,
                         BigDecimal price_for_night,
                         Address address,
                         User user) {
        this.title = title;
        this.description = description;
        this.price_for_night = price_for_night;
        this.address = address;
    }

    public void addImage(Image image){
        this.images.add(image);
    }

    public void addReservation(Reservation reservation){
        this.reservations.add(reservation);
    }

}

