package com.sparta.nbcamblog.dto;

import com.sparta.nbcamblog.entity.Blog;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BlogResponseDto {
    private Long id;
    private String username;
    private String title;
    private String content;
    private LocalDateTime createdat;
    private LocalDateTime modifiedat;

    public BlogResponseDto(Blog blog) {
        this.id = blog.getId();
        this.username = blog.getUsername();
        this.title = blog.getTitle();
        this.content = blog.getContent();
        this.createdat = blog.getCreateAt();
        this.modifiedat = blog.getModifiedAt();
    }
}
