
package com.project.bookingnetic.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.thymeleaf.expression.Maps;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    @Column(name = "creation_date")
    private LocalDate creation_date;

    @OneToOne()
    @JoinColumn(name = "address_fk", referencedColumnName = "id")
    private Address address;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_fk", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;


    public Accommodation(String title,
                         String description,
                         BigDecimal price_for_night,
                         Address address,
                         User user) {
        this.title = title;
        this.description = description;
        this.price_for_night = price_for_night;
        this.address = address;
        this.user = user;
    }


}

