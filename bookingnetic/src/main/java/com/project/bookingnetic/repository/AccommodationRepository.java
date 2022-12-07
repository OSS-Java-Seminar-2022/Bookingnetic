package com.project.bookingnetic.repository;

import com.project.bookingnetic.models.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
}