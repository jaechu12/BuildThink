package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "brick")
@EntityListeners(AuditingEntityListener.class)
public class Brick {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(name = "content")
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime created;


    public Brick() {
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setUsers(Users users) {
        this.user = users;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }


    public Long getId() {
        return id;
    }

    public Users getUsers() {
        return user;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreated() {
        return created;
    }


}

