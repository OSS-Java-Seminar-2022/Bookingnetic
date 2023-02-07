package com.project.bookingnetic.repository;

import com.project.bookingnetic.models.Accommodation;
import com.project.bookingnetic.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query(value = "SELECT * FROM reservation r WHERE r.user_fk = :user_fk ",nativeQuery = true)
    List<Reservation> getReservationsByUserFk(@Param("user_fk") long user_fk);

    List<Reservation> findAllByAccommodation_Id(Long id);
}