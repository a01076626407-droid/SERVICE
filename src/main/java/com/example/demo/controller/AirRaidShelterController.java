package com.example.demo.controller;

import com.example.demo.domain.AirRaidShelter;
import com.example.demo.service.AirRaidShelterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

// 1. JSON 형식의 HTTP 응답 본문(Body) 반환
@RestController
// 2. 공통 기본 URL 경로 매핑 (민방공 대피소 API)
@RequestMapping("/api/shelters/air")
// 3. final 필드 생성자 자동 주입 (Lombok)
@RequiredArgsConstructor
// 4. 프론트엔드 연동 CORS 정책 허용
@CrossOrigin(origins = "*")
public class AirRaidShelterController {

    // 비즈니스 로직 Service 객체 주입
    private final AirRaidShelterService airRaidShelterService;

    /**
     * 💡 [민방공 대피소 목록 조회 API - 전체 조회 및 화면 바운딩 박스 검색 겸용]
     */
    @GetMapping
    public ResponseEntity<List<AirRaidShelter>> getAirRaidShelters(
            // 화면 사각 영역의 최소 위도 (필수값 아님)
            @RequestParam(value = "minLat", required = false) Double minLat,
            // 화면 사각 영역의 최대 위도 (필수값 아님)
            @RequestParam(value = "maxLat", required = false) Double maxLat,
            // 화면 사각 영역의 최소 경도 (필수값 아님)
            @RequestParam(value = "minLng", required = false) Double minLng,
            // 화면 사각 영역의 최대 경도 (필수값 아님)
            @RequestParam(value = "maxLng", required = false) Double maxLng
    ) {
        // 💡 1. 프론트엔드에서 지도 뷰포트 사각 영역 좌표 4개가 모두 넘어왔을 때
        if (minLat != null && maxLat != null && minLng != null && maxLng != null) {
            List<AirRaidShelter> areaShelters = airRaidShelterService.findSheltersInBounds(minLat, maxLat, minLng, maxLng);
            
            // 💡 [방어 로직] 
            // 만약 쿼리 범위에 걸리지 않더라도 'USER_'로 시작하는 어드민 커스텀 데이터를 강제로 합쳐서 클라이언트에 보냅니다.
            List<AirRaidShelter> allShelters = airRaidShelterService.findAllAirRaidShelters();
            List<AirRaidShelter> userCustomShelters = allShelters.stream()
                    .filter(s -> s.getShltId() != null && s.getShltId().startsWith("USER_"))
                    .collect(Collectors.toList());

            // areaShelters에 이미 포함된 게 아니라면 강제로 추가
            for (AirRaidShelter custom : userCustomShelters) {
                boolean exists = areaShelters.stream().anyMatch(s -> s.getShltId().equals(custom.getShltId()));
                if (!exists) {
                    areaShelters.add(custom);
                }
            }

            return ResponseEntity.ok(areaShelters);
        }

        // 💡 2. 좌표 파라미터가 없는 기본 호출일 때 (전체 목록)
        List<AirRaidShelter> allShelters = airRaidShelterService.findAllAirRaidShelters();
        return ResponseEntity.ok(allShelters);
    }

    /**
     * 특정 민방공 대피소 1개 단건 상세 조회
     * 💡 [필수 수정] 어드민 데이터의 ID가 문자열(USER_...)이므로 @PathVariable 타입을 String으로 받아야 500 에러를 방지합니다.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AirRaidShelter> getShelterById(@PathVariable("id") String id) {
        AirRaidShelter shelter = airRaidShelterService.findShelterById(id);
        return ResponseEntity.ok(shelter);
    }
}