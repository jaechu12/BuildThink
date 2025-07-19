package com.example.demo.Model;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@Table(name = "summary")
@EntityListeners(AuditingEntityListener.class)
public class Summary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userid;

    @Column(name = "week_start_date")
    private LocalDate weekStartDate;

    @Column(name = "brick_count")
    private Long brickCount;

    @Column(name = "building_type")
    private String buildingType;

    public Summary() {
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public void setWeekStartDate(LocalDate weekStartDate) {
        this.weekStartDate = weekStartDate;
    }

    public void setBrickCount(Long brickCount) {
        this.brickCount = brickCount;
    }

    public void setBuildingType(String buildingType) {
        this.buildingType = buildingType;
    }


    public Long getId() {
        return id;
    }

    public Long getUserid() {
        return userid;
    }

    public LocalDate getWeekStartDate() {
        return weekStartDate;
    }

    public Long getBrickCount() {
        return brickCount;
    }

    public String getBuildingType() {
        return buildingType;
    }

}

