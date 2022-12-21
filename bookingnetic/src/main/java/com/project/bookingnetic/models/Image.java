package com.project.bookingnetic.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString
@NoArgsConstructor
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "image_id")
    private long id;

    @Column(name = "img")
    private byte[] img ;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(name = "accommodation_id", referencedColumnName = "accommodation_id", insertable=false, updatable=false)
    private Accommodation accommodationId;

    public Image(byte[] img, String description, Accommodation accommodationId) {
        this.img = img;
        this.description = description;
        this.accommodationId = accommodationId;
    }
}
