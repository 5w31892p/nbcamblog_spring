package com.sparta.nbcamblog.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.nbcamblog.entity.Blog;
import com.sparta.nbcamblog.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlogResponseDto {
    private Long id;
    private String username;
    private String title;
    private String content;
    private LocalDateTime createdat;
    private LocalDateTime modifiedat;
    private List<CommentResponseDto> commentList;

    public BlogResponseDto(Blog blog) {
        List<CommentResponseDto> list = new ArrayList<>();
        this.id = blog.getId();
        this.username = blog.getUsername();
        this.title = blog.getTitle();
        this.content = blog.getContent();
        this.createdat = blog.getCreateAt();
        this.modifiedat = blog.getModifiedAt();
        for (Comment comment : blog.getCommentList()){
            list.add(new CommentResponseDto(comment));
        }
        this.commentList =list;
    }
}
