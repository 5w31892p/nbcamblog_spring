package com.example.nbcamblog.entity;

import com.example.nbcamblog.dto.BlogRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@Entity
@NoArgsConstructor
public class Blog extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    public Blog (String author, String password, String title, String content) {
        this.author = author;
        this.password = password;
        this.title = title;
        this.content = content;
    }

    public Blog (BlogRequestDto blogRequestDto) {
        this.author = blogRequestDto.getAuthor();
        this.password = blogRequestDto.getPassword();
        this.title = blogRequestDto.getTitle();
        this.content = blogRequestDto.getContent();
    }

    public void update (BlogRequestDto blogRequestDto) {
        this.author = blogRequestDto.getAuthor();
        this.password = blogRequestDto.getPassword();
        this.title = blogRequestDto.getTitle();
        this.content = blogRequestDto.getContent();
    }
}
