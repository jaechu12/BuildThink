package com.example.demo.Repository;
import com.example.demo.Model.Summary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SummaryRepository extends JpaRepository<Summary, Long> {
    Optional<Summary> findById(Long id);

    Summary save(Summary summary);
}
