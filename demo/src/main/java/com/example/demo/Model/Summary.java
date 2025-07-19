package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@Table(name = "summary")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
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

}
