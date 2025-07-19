package com.example.demo.DTO;

import java.time.LocalDate;

public class SummaryDTO {
    private Long userId;
    private LocalDate weekStartDate;
    private long brickCount;
    private String buildingType;

    // 생성자, getter, setter

    public SummaryDTO(Long userId, LocalDate weekStartDate, long brickCount, String buildingType) {
        this.userId = userId;
        this.weekStartDate = weekStartDate;
        this.brickCount = brickCount;
        this.buildingType = buildingType;
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public LocalDate getWeekStartDate() { return weekStartDate; }
    public void setWeekStartDate(LocalDate weekStartDate) { this.weekStartDate = weekStartDate; }

    public long getBrickCount() { return brickCount; }
    public void setBrickCount(long brickCount) { this.brickCount = brickCount; }

    public String getBuildingType() { return buildingType; }
    public void setBuildingType(String buildingType) { this.buildingType = buildingType; }
}