package com.example.demo.Repository;
import com.example.demo.Model.BuildingStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BuildingStatRepository extends JpaRepository<BuildingStat, Long> {
    Optional<BuildingStat> findById(Long id);

    BuildingStat save(BuildingStat buildingStat);
}
