package com.project.bookingnetic.repository;

import com.project.bookingnetic.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    @Query(value = "SELECT * FROM image where accommodation_fk = :accomm_fk",nativeQuery = true)
    List<Image> findByAccommodationFk(@Param("accomm_fk")long accommodation_fk);
}