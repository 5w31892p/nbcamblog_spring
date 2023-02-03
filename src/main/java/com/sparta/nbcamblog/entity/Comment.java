package com.sparta.nbcamblog.entity;

import java.util.ArrayList;
import java.util.List;

import com.sparta.nbcamblog.dto.CommentRequestDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<NestedReply> replyList = new ArrayList<>();

    @ManyToMany
    private List<BlogUser> like = new ArrayList<>();


    public Comment(CommentRequestDto requestDto, BlogUser user, Blog blog) {
        this.user = user;
        this.blog = blog;
        this.comment = requestDto.getComment();
    }

    public void update(CommentRequestDto requestDto) {
        this.comment = requestDto.getComment();
    }
}
