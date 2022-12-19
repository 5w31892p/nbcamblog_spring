package com.sparta.nbcamblog.dto;

import com.sparta.nbcamblog.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private Long postId;
    private String username;
    private String comment;
    private LocalDateTime createAt;
    private LocalDateTime modifiedat;

    public CommentResponseDto(Comment comment) {
        this.postId = comment.getBlog().getId();
        this.username = comment.getUser().getUsername();
        this.comment = comment.getComment();
        this.createAt = comment.getCreateAt();
        this.modifiedat = comment.getModifiedAt();
    }
}
