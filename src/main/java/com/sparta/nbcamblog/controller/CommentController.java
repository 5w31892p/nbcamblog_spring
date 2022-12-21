package com.sparta.nbcamblog.controller;

import com.sparta.nbcamblog.dto.AuthenticatedUser;
import com.sparta.nbcamblog.dto.CommentRequestDto;
import com.sparta.nbcamblog.dto.CommentResponseDto;
import com.sparta.nbcamblog.exception.StatusResponse;
import com.sparta.nbcamblog.jwt.JwtUtil;
import com.sparta.nbcamblog.service.CommentService;
import com.sparta.nbcamblog.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
}
