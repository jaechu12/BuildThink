package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "building_stat")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
public class BuildingStat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Users userid;

    @Column(name = "house_count")
    private Long houseCount = 0L;

    @Column(name = "villa_count")
    private Long villaCount = 0L;

    @Column(name = "apartment_count")
    private Long apartmentCount = 0L;

    @Column(name = "tower_count")
    private Long towerCount = 0L;
}