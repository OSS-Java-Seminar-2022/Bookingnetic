package com.project.bookingnetic.repository;

import com.project.bookingnetic.models.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

//    @Query(value = "SELECT * FROM accommodation a WHERE a.user_fk = :user_fk ",nativeQuery = true)
//    Accommodation getAccommodationByUserFk(@Param("user_fk") long user_fk);

    List<Accommodation> findAllByAddress_City(String city);

}