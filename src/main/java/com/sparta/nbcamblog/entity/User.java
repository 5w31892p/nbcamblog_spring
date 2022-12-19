package com.sparta.nbcamblog.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;


    @OneToMany
    private List<Blog> blogList = new ArrayList<>();

    public User (String username, String password) {
        this.username = username;
        this.password = password;
    }
}
