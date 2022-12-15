package com.sparta.nbcamblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NbcamBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(NbcamBlogApplication.class, args);
    }

}
