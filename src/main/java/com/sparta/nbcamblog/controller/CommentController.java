package com.sparta.nbcamblog.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.nbcamblog.dto.AuthenticatedUser;
import com.sparta.nbcamblog.dto.CommentRequestDto;
import com.sparta.nbcamblog.dto.CommentResponseDto;
import com.sparta.nbcamblog.exception.StatusResponse;
import com.sparta.nbcamblog.jwt.JwtUtil;
import com.sparta.nbcamblog.service.CommentService;
import com.sparta.nbcamblog.service.JwtService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;
    private final JwtUtil jwtUtil;
    private final JwtService jwtService;

    @PostMapping("post/{postId}/comment")
    public CommentResponseDto createComment(@PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        AuthenticatedUser authenticatedUser = jwtService.validateAndGetInfo(token);
        return commentService.createComment(postId, commentRequestDto, authenticatedUser.getUsername());
    }

    @GetMapping("post/{postId}/comments")
    public List<CommentResponseDto> getComments(@PathVariable Long postId) {
        return commentService.getComments(postId);
    }

    @PutMapping("post/{postId}/comment/{id}")
    public CommentResponseDto updateComment (@PathVariable Long id, @PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        AuthenticatedUser authenticatedUser = jwtService.validateAndGetInfo(token);
        return commentService.updateComment(postId, id, commentRequestDto, authenticatedUser.getUsername());
    }

    @DeleteMapping("post/{postId}/comment/{id}")
    public StatusResponse deleteComment (@PathVariable Long id, @PathVariable Long postId, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        AuthenticatedUser authenticatedUser = jwtService.validateAndGetInfo(token);
        return commentService.deleteComment(postId, id,authenticatedUser.getUsername());
    }

    @GetMapping("commentlike/{commentId}")
    public StatusResponse addLike(@PathVariable Long commentId, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        AuthenticatedUser authenticatedUser = jwtService.validateAndGetInfo(token);
        return this.commentService.addLike(commentId, authenticatedUser.getUsername());
    }
}
