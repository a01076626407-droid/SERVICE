package com.example.demo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "airstrike")
public class AirRaidShelter {

    // 💡 1. 자동 증가(@GeneratedValue)를 삭제하고, 타입을 String으로 통일했습니다.
    // 💡 2. DB의 'shlt_id' 컬럼과 자바의 'shltId' 변수를 완벽하게 1:1로 매핑했습니다.
    @Id
    @Column(name = "shlt_id")
    private String shltId;

    private String ctpv_nm;
    private String sgg_nm;
    private String fclt_nm;
    private String daddr;

    @Column(name = "lot", precision = 10, scale = 7)
    private BigDecimal lot;

    @Column(name = "lat", precision = 10, scale = 7)
    private BigDecimal lat;

    private String mng_dept_nm;
}