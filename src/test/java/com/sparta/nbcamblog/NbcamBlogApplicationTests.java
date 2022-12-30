package com.sparta.nbcamblog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sparta.nbcamblog.dto.SignupRequestDto;
import com.sparta.nbcamblog.security.UserSecurityService;
import com.sparta.nbcamblog.service.UserService;

@SpringBootTest
class NbcamBlogApplicationTests {

    @Autowired
    private UserSecurityService securityService;
    @Autowired
    private UserService service;
    @Autowired
    @Test
    void contextLoads() {
        String username = "asf123";
        String password = "asdf123!!";
        boolean admin = false;
        this.service.signup(new SignupRequestDto());
    }

}
