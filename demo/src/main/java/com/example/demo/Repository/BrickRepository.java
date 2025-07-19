package com.example.demo.Repository;

import com.example.demo.Model.Brick;
import com.example.demo.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BrickRepository extends JpaRepository<Brick, Long> {
    Optional<Brick> findById(Long id);

    Brick save(Brick brick);

    List<Brick> findByUserAndCreatedBetween(Users user, LocalDateTime start, LocalDateTime end);

    List<Brick> findAllByUserAndCreatedAfter(Users user, LocalDateTime created);

    boolean existsByUserAndCreatedBetween(Users user, LocalDateTime start, LocalDateTime end);
}
