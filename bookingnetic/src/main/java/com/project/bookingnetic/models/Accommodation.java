
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
@Table(name = "accommodation")
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "_name")
    private String name;
    @Column(name = "description")
    private String description;
    @OneToOne
    @JoinColumn(name = "address_fk", referencedColumnName = "id", insertable=false, updatable=false)
    private Address address;
    @OneToOne
    @JoinColumn(name = "user_fk", referencedColumnName = "user_id", insertable=false, updatable=false)
    private User accountID;
    @Column(name = "price")
    private BigDecimal price;
}

