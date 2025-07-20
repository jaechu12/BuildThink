package com.example.demo.Service;

import com.example.demo.DTO.RequestDTO.UserLoginDTO;
import com.example.demo.DTO.RequestDTO.UserRegisterDTO;
import com.example.demo.Model.BuildingStat;
import com.example.demo.Model.Users;
import com.example.demo.Repository.BuildingStatRepository;
import com.example.demo.Repository.UsersRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginService {

    @PersistenceContext
    private EntityManager em;

    private final JdbcTemplate jdbcTemplate;
    private final UsersRepository usersRepository;
    private final BuildingStatRepository buildingStatRepository;

    public LoginService(JdbcTemplate jdbcTemplate, UsersRepository usersRepository, BuildingStatRepository buildingStatRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.usersRepository = usersRepository;
        this.buildingStatRepository = buildingStatRepository;
    }


    @Transactional
    public Users registUsers(UserRegisterDTO registerDTO) {
        Users user = new Users();
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());

        Users savedUser = usersRepository.save(user);

        BuildingStat stat = new BuildingStat();
        stat.setUserid(savedUser);
        buildingStatRepository.save(stat);

        return savedUser;
    }

    public Users login(UserLoginDTO loginDTO) {
        return usersRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
    }
}
