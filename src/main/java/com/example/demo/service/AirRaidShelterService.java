package com.example.demo.service;

import com.example.demo.domain.AirRaidShelter;
import com.example.demo.repository.AirRaidShelterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AirRaidShelterService {

    private final AirRaidShelterRepository airRaidShelterRepository;

    public List<AirRaidShelter> findAllAirRaidShelters() {
        return airRaidShelterRepository.findAll();
    }

    public List<AirRaidShelter> findSheltersInBounds(Double minLat, Double maxLat, Double minLng, Double maxLng) {
        return airRaidShelterRepository.findSheltersInBounds(minLat, maxLat, minLng, maxLng);
    }

    public AirRaidShelter findShelterById(String id) {
        if (id != null && id.startsWith("USER_")) {
            return airRaidShelterRepository.findByShltId(id)
                    .orElseThrow(() -> new IllegalArgumentException("해당 어드민 커스텀 대피소가 존재하지 않습니다. id=" + id));
        } else {
            try {
                Long longId = Long.valueOf(id);
                return airRaidShelterRepository.findById(longId)
                        .orElseThrow(() -> new IllegalArgumentException("해당 대피소가 존재하지 않습니다. id=" + id));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("유효하지 않은 대피소 ID 형식입니다. id=" + id);
            }
        }
    }
}