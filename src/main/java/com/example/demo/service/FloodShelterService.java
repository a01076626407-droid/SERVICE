package com.example.demo.service;

import com.example.demo.domain.FloodShelter;
import com.example.demo.repository.FloodShelterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FloodShelterService {

    private final FloodShelterRepository floodShelterRepository;

    public List<FloodShelter> findAllFloodShelters() {
        return floodShelterRepository.findAll();
    }

    public List<FloodShelter> findSheltersInBounds(Double minLat, Double maxLat, Double minLng, Double maxLng) {
        return floodShelterRepository.findSheltersInBounds(minLat, maxLat, minLng, maxLng);
    }

    // 💡 [수정] String 타입 id로 단건 조회 처리
    public FloodShelter findShelterById(String id) {
        return floodShelterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 대피소가 존재하지 않습니다: " + id));
    }
}