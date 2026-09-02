package com.example.demo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "flood")
public class FloodShelter {

    // 💡 [핵심] 두 개로 나뉘어 있던 ID를 하나로 합치고 깔끔하게 정리했습니다.
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