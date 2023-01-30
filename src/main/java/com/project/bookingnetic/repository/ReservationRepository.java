package com.project.bookingnetic.repository;

import com.project.bookingnetic.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {7

    List<Reservation> findAllByAccommodation_Id(Long id);
}