package com.sparta.nbcamblog.entity;

import com.sparta.nbcamblog.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "BLOG_ID", nullable = false)
    private Blog blog;

    public Comment(CommentRequestDto requestDto, User user, Blog blog) {
        this.user = user;
        this.blog = blog;
        this.comment = requestDto.getComment();
    }


    public void update(CommentRequestDto requestDto) {
        this.comment = requestDto.getComment();
    }
}
