package com.project.bookingnetic.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccommImagePair {

    private Accommodation accommodation;
    private String image;

    public AccommImagePair(Accommodation accommodation, String image) {
        this.accommodation = accommodation;
        this.image = image;
    }
}
