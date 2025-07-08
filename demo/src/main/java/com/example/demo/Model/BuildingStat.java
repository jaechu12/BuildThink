package com.example.demo.Model;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "building_stat")
@EntityListeners(AuditingEntityListener.class)
public class BuildingStat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userid;

    @Column(name = "house_count")
    private Long houseCount;

    @Column(name = "villa_count")
    private Long villaCount;

    @Column(name = "apartment_count")
    private Long apartmentCount;

    @Column(name = "tower_count")
    private Long towerCount;

    public BuildingStat() {
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public void setHouseCount(Long houseCount) {
        this.houseCount = houseCount;
    }

    public void setVillaCount(Long villaCount) {
        this.villaCount = villaCount;
    }

    public void setApartmentCount(Long apartmentCount) {
        this.apartmentCount = apartmentCount;
    }

    public void setTowerCount(Long towerCount) {
        this.towerCount = towerCount;
    }


    public Long getId() {
        return id;
    }

    public Long getUserid() {
        return userid;
    }

    public Long getHouseCount() {
        return houseCount;
    }

    public Long getVillaCount() {
        return villaCount;
    }

    public Long getApartmentCount() {
        return apartmentCount;
    }

    public Long getTowerCount() {
        return towerCount;
    }


}

