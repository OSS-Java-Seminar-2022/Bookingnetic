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
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "reservation_date")
    private Date reservationDate;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable=false, updatable=false)
    private User userId;

    @OneToOne
    @JoinColumn(name = "accommodation_id", referencedColumnName = "accommodation_id", insertable=false, updatable=false)
    private Accommodation accommodationId;

    public Reservation(Date reservationDate, Date startDate, Date endDate, BigDecimal totalPrice, User userId, Accommodation accommodationId) {
        this.reservationDate = reservationDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
        this.userId = userId;
        this.accommodationId = accommodationId;
    }

}
