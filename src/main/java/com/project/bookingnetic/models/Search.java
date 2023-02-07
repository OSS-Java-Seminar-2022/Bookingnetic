package com.project.bookingnetic.models;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Search {

    private String city;
    private LocalDate dateFrom;
    private LocalDate dateTo;
}
