package com.sparta.nbcamblog.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.sparta.nbcamblog.dto.CommentRequestDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private BlogUser user;

    @ManyToOne
    @JoinColumn(name = "BLOG_ID", nullable = false)
    private Blog blog;

    @ManyToMany
    Set<BlogUser> voter;


    public Comment(CommentRequestDto requestDto, BlogUser user, Blog blog) {
        this.user = user;
        this.blog = blog;
        this.comment = requestDto.getComment();
    }


    public void update(CommentRequestDto requestDto) {
        this.comment = requestDto.getComment();
    }
}
