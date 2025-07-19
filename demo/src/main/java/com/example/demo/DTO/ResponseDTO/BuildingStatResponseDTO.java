package com.example.demo.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BuildingStatResponseDTO {
    private String buildingType;
    private int brickCount;
    private String userName;
}