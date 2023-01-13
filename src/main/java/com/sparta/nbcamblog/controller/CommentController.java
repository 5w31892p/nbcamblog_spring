package com.sparta.nbcamblog.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.nbcamblog.dto.CommentRequestDto;
import com.sparta.nbcamblog.dto.CommentResponseDto;
import com.sparta.nbcamblog.exception.StatusResponse;
import com.sparta.nbcamblog.service.CommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("post/{postId}/comment")
    public CommentResponseDto createComment(@PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetails userDetails) {
        return commentService.createComment(postId, commentRequestDto, userDetails.getUsername());
    }

    @GetMapping("post/{postId}/comments")
    public List<CommentResponseDto> getComments(@PathVariable Long postId) {
        return commentService.getComments(postId);
    }

    @PutMapping("post/{postId}/comment/{id}")
    public CommentResponseDto updateComment (@PathVariable Long id, @PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetails userDetails) {
        return commentService.updateComment(postId, id, commentRequestDto, userDetails.getUsername());
    }

    @DeleteMapping("post/{postId}/comment/{id}")
    public StatusResponse deleteComment (@PathVariable Long id, @PathVariable Long postId, @AuthenticationPrincipal UserDetails userDetails) {
        return commentService.deleteComment(postId, id,userDetails.getUsername());
    }

    @GetMapping("commentlike/{commentId}")
    public StatusResponse addLike(@PathVariable Long commentId, @AuthenticationPrincipal UserDetails userDetails) {
        return this.commentService.addLike(commentId, userDetails.getUsername());
    }
}
