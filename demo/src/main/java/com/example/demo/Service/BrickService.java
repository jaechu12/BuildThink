package com.example.demo.Service;

import com.example.demo.Model.Brick;
import com.example.demo.Repository.BrickRepository;
import com.example.demo.Repository.UsersRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BrickService {

    @PersistenceContext
    private EntityManager em;

    private JdbcTemplate jdbcTemplate;
    private BrickRepository brickRepository;

    public BrickService(JdbcTemplate jdbcTemplate, BrickRepository brickRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.brickRepository = brickRepository;
    }

    @Transactional
    public Brick brickPost(Brick brick){
        return brickRepository.save(brick);
    }

}
