package com.example.demo.repository;

import com.example.demo.domain.FloodShelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
// 💡 [수정] JpaRepository 타입을 String으로 변경
public interface FloodShelterRepository extends JpaRepository<FloodShelter, String> {

    // 💡 [수정] 네이티브 쿼리로 변경하여 USER_ 커스텀 데이터가 바운딩 박스를 무시하고 조회되도록 처리
    @Query(value = "SELECT * FROM flood WHERE " + 
                   "(((CAST(lat AS DOUBLE) BETWEEN :minLat AND :maxLat) AND (CAST(lot AS DOUBLE) BETWEEN :minLng AND :maxLng)) " +
                   "OR " +
                   "((CAST(lot AS DOUBLE) BETWEEN :minLat AND :maxLat) AND (CAST(lat AS DOUBLE) BETWEEN :minLng AND :maxLng))) " +
                   "OR shlt_id LIKE 'USER_%'", 
           nativeQuery = true)
    List<FloodShelter> findSheltersInBounds(
            @Param("minLat") Double minLat,
            @Param("maxLat") Double maxLat,
            @Param("minLng") Double minLng,
            @Param("maxLng") Double maxLng
    );
}