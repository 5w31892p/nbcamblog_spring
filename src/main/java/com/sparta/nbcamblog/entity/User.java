package com.sparta.nbcamblog.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
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
    @Pattern(regexp="^[a-z0-9]{4,10}$", message = "아이디는 영어(소문자)와 숫자를 포함해서 4~10자리 이내로 입력해주세요.")
    private String username;

    @Column(nullable = false)
    @Pattern(regexp="^[a-zA-Z0-9]{8,15}$", message = "비밀번호는 영어(대소문자)와 숫자를 포함해서 4~10자리 이내로 입력해주세요.")
    private String password;


    @OneToMany
    private List<Blog> blogList = new ArrayList<>();

    public User (String username, String password) {
        this.username = username;
        this.password = password;
    }
}
