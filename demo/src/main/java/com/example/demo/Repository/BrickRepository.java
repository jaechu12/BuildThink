package com.example.demo.Repository;

import com.example.demo.Model.Brick;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrickRepository extends JpaRepository<Brick, Long> {
    Optional<Brick> findById(Long id);

    Brick save(Brick brick);
}
