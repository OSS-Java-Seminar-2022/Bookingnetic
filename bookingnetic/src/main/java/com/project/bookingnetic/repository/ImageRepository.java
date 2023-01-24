package com.project.bookingnetic.repository;

import com.project.bookingnetic.models.Address;
import com.project.bookingnetic.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Long> {

}