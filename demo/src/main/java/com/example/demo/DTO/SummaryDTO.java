package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SummaryDTO {
    private Long userId;
    private LocalDate weekStartDate;
    private long brickCount;
    private String buildingType;
}