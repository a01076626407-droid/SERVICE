package com.example.demo.controller;

import com.example.demo.domain.FloodShelter;
import com.example.demo.service.FloodShelterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/shelters/flood")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FloodShelterController {

    private final FloodShelterService floodShelterService;

    @GetMapping
    public ResponseEntity<List<FloodShelter>> getFloodShelters(
            @RequestParam(value = "minLat", required = false) Double minLat,
            @RequestParam(value = "maxLat", required = false) Double maxLat,
            @RequestParam(value = "minLng", required = false) Double minLng,
            @RequestParam(value = "maxLng", required = false) Double maxLng
    ) {
        if (minLat != null && maxLat != null && minLng != null && maxLng != null) {
            List<FloodShelter> areaShelters = floodShelterService.findSheltersInBounds(minLat, maxLat, minLng, maxLng);

            // 💡 [추가] 어드민 커스텀 데이터를 강제로 리스트에 포함시키는 방어 로직
            List<FloodShelter> allShelters = floodShelterService.findAllFloodShelters();
            List<FloodShelter> userCustomShelters = allShelters.stream()
                    // 💡 [핵심 수정] getShlt_id() -> getShltId()로 모두 변경했습니다!
                    .filter(s -> s.getShltId() != null && s.getShltId().startsWith("USER_"))
                    .collect(Collectors.toList());

            for (FloodShelter custom : userCustomShelters) {
                // 💡 [핵심 수정] 여기도 getShltId()로 짝을 맞추었습니다!
                boolean exists = areaShelters.stream().anyMatch(s -> s.getShltId().equals(custom.getShltId()));
                if (!exists) {
                    areaShelters.add(custom);
                }
            }

            return ResponseEntity.ok(areaShelters);
        }

        List<FloodShelter> allShelters = floodShelterService.findAllFloodShelters();
        return ResponseEntity.ok(allShelters);
    }

    // 💡 [수정] 단건 조회 파라미터 타입을 String으로 변경
    @GetMapping("/{id}")
    public ResponseEntity<FloodShelter> getShelterById(@PathVariable("id") String id) {
        FloodShelter shelter = floodShelterService.findShelterById(id);
        return ResponseEntity.ok(shelter);
    }
}