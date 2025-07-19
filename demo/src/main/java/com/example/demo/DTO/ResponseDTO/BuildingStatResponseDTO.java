package com.example.demo.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BuildingStatResponseDTO {
    private Long id;
    private Long userId;
    private Long houseCount;
    private Long villaCount;
    private Long apartmentCount;
    private Long towerCount;
}