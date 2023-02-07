package com.project.bookingnetic.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateReservation {

    private LocalDate dateTo;
    private LocalDate dateFrom;
    private BigDecimal totalPrice;
    private long userId;
    private long accommodationId;
}
