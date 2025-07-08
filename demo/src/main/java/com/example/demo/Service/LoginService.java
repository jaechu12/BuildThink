package com.example.demo.Service;

import com.example.demo.Model.Users;
import com.example.demo.Repository.UsersRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @PersistenceContext
    private EntityManager em;

    private final JdbcTemplate jdbcTemplate;
    private final UsersRepository usersRepository;

    public LoginService(JdbcTemplate jdbcTemplate, UsersRepository usersRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.usersRepository = usersRepository;
    }


    public Users login(String email, String password) {
        Users users = usersRepository.findByEmail(email);

        if (users == null) {
            return null;
        }

        if (users.getPassword().equals(password)) {
            System.out.println("d");
            return users;
        }

        return null;
    }
}
