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

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    public Blog (BlogRequestDto blogRequestDto, User user) {
        this.username = user.getUsername();
        this.title = blogRequestDto.getTitle();
        this.content = blogRequestDto.getContent();
        this.user = user;
    }

    public void update (BlogRequestDto blogRequestDto) {
        this.title = blogRequestDto.getTitle();
        this.content = blogRequestDto.getContent();
    }
}
