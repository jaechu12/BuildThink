package com.example.demo.Service;

import com.example.demo.DTO.RequestDTO.BrickCreateDTO;
import com.example.demo.Model.Brick;
import com.example.demo.Model.Users;
import com.example.demo.Repository.BrickRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

    public void brickPost(BrickCreateDTO brickDTO, Users loginMember) {
        Brick brick = new Brick();
        brick.setContent(brickDTO.getContent());
        brick.setUser(loginMember);
        brick.setCreated(LocalDateTime.now());

        brickRepository.save(brick);
    }

}
