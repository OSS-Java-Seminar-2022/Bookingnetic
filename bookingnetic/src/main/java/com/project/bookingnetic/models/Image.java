package com.project.bookingnetic.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.JdbcTypeCode;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Types;

@Entity
@Data
@ToString
@NoArgsConstructor
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Lob
    @JdbcTypeCode(Types.BINARY)
    @Column(name = "img")
    private byte[] img ;

    @Column(name = "description")
    private String description;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "accommodation_fk", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Accommodation accommodation;


    public Image(byte[] img, String description, Accommodation accommodation) {
        this.img = img;
        this.description = description;
        this.accommodation = accommodation;
    }



    public static Image setImage(String filepath, String formatName, String description, Accommodation accommodation) throws IOException {
        BufferedImage bufferimage = ImageIO.read(new File(filepath));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageIO.write(bufferimage, formatName, output );
        byte [] data = output.toByteArray();
        return new Image(data, description, accommodation);
    }



}
