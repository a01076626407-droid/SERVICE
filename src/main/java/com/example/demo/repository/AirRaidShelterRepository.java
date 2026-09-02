package com.example.demo.repository;

import com.example.demo.domain.AirRaidShelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AirRaidShelterRepository extends JpaRepository<AirRaidShelter, Long> {

    @Query(value = "SELECT * FROM airstrike WHERE " + 
                   "(((CAST(lat AS DOUBLE) BETWEEN :minLat AND :maxLat) AND (CAST(lot AS DOUBLE) BETWEEN :minLng AND :maxLng)) " +
                   "OR " +
                   "((CAST(lot AS DOUBLE) BETWEEN :minLat AND :maxLat) AND (CAST(lat AS DOUBLE) BETWEEN :minLng AND :maxLng))) " +
                   "OR shltid LIKE 'USER_%'", 
           nativeQuery = true)
    List<AirRaidShelter> findSheltersInBounds(@Param("minLat") Double minLat,
                                            @Param("maxLat") Double maxLat,
                                            @Param("minLng") Double minLng,
                                            @Param("maxLng") Double maxLng);
                                            
    Optional<AirRaidShelter> findByShltId(String shltId);
}