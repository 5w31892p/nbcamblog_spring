package com.sparta.nbcamblog.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.sparta.nbcamblog.entity.Comment;
import com.sparta.nbcamblog.entity.NestedReply;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private Long postId;
    private Long id;
    private String username;
    private String comment;
    private LocalDateTime createAt;
    private LocalDateTime modifiedat;
    private int like;
    private List<NestedReplyResponseDto> replyList;

    public CommentResponseDto(Comment comment) {
        this.postId = comment.getBlog().getId();
        this.id = comment.getId();
        this.username = comment.getUser().getUsername();
        this.comment = comment.getComment();
        this.createAt = comment.getCreateAt();
        this.modifiedat = comment.getModifiedAt();
        this.like = comment.getLike().size();
        List<NestedReplyResponseDto> list = new ArrayList<>();
        for (NestedReply reply : comment.getReplyList()) {
            list.add(new NestedReplyResponseDto(reply));
        }
        this.replyList = list;
    }
}
