package com.project.bookingnetic.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class AccommUserPair {
    private List<Accommodation> accommodations = new ArrayList<>();
    private User user;

    public AccommUserPair(List<Accommodation> accommodations, User user) {
        this.accommodations = accommodations;
        this.user = user;
    }
}
