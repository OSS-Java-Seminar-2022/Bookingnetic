package com.project.bookingnetic.repository;

import com.project.bookingnetic.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByAccommodation_Id(Long id);
}