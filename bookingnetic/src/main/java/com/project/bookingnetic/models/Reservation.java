package com.project.bookingnetic.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "reservation_date")
    private LocalDate reservation_date;

    @Column(name = "check_in")
    private LocalDate checkIn;

    @Column(name="check_out")
    private LocalDate checkOut;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_fk", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="accommodation_fk", nullable = false)
    private Accommodation accommodation;

    public Reservation(LocalDate checkIn,
                       LocalDate checkOut,
                       BigDecimal price,
                       User user,
                       Accommodation accommodation) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.price = price;
        this.user = user;
        this.accommodation = accommodation;
    }
}
