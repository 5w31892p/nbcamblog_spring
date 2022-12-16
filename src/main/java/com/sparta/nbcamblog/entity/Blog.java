package com.sparta.nbcamblog.entity;

import com.sparta.nbcamblog.dto.BlogRequestDto;
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
    private String username;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    public Blog (String username, String title, String content) {
        this.username = username;
        this.title = title;
        this.content = content;
    }

    public Blog (BlogRequestDto blogRequestDto) {
        this.username = blogRequestDto.getUsername();
        this.title = blogRequestDto.getTitle();
        this.content = blogRequestDto.getContent();
    }

    public void update (BlogRequestDto blogRequestDto) {
        this.username = blogRequestDto.getUsername();
        this.title = blogRequestDto.getTitle();
        this.content = blogRequestDto.getContent();
    }
}
