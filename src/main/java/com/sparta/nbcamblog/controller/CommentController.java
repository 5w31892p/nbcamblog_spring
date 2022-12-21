package com.sparta.nbcamblog.controller;

import com.sparta.nbcamblog.dto.CommentRequestDto;
import com.sparta.nbcamblog.dto.CommentResponseDto;
import com.sparta.nbcamblog.exception.StatusResponse;
import com.sparta.nbcamblog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("post/{postId}/comment")
    public CommentResponseDto createComment(@PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        return commentService.createComment(postId, commentRequestDto, request);
    }

    @GetMapping("post/{postId}/comments")
    public List<CommentResponseDto> getComments(@PathVariable Long postId) {
        return commentService.getComments(postId);
    }

    @PutMapping("post/{postId}/comment/{id}")
    public CommentResponseDto updateComment (@PathVariable Long id, @PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        return commentService.updateComment(postId, id, commentRequestDto, request);
    }

    @DeleteMapping("post/{postId}/comment/{id}")
    public StatusResponse deleteComment (@PathVariable Long id, @PathVariable Long postId, HttpServletRequest request) {
        return commentService.deleteComment(postId, id, request);
    }
}
