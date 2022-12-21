package com.sparta.nbcamblog.controller;


import com.sparta.nbcamblog.dto.AuthenticatedUser;
import com.sparta.nbcamblog.dto.BlogRequestDto;
import com.sparta.nbcamblog.dto.BlogResponseDto;
import com.sparta.nbcamblog.exception.StatusResponse;
import com.sparta.nbcamblog.jwt.JwtUtil;
import com.sparta.nbcamblog.service.BlogService;
import com.sparta.nbcamblog.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;
    private final JwtUtil jwtUtil;
    private final JwtService jwtService;

    @PostMapping("/post")
    public BlogResponseDto createContent(@RequestBody BlogRequestDto blogRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        AuthenticatedUser authenticatedUser = jwtService.validateAndGetInfo(token);
        return blogService.createContent(blogRequestDto, authenticatedUser.getUsername());
    }

    @GetMapping("/posts")
    public List<BlogResponseDto> getContents() {
        return blogService.getContents();
    }

    @GetMapping("/post/{id}")
    public BlogResponseDto getContent (@PathVariable Long id) {
        return blogService.getContent(id);
    }

    @PutMapping("/post/{id}")
    public BlogResponseDto updateContent (@PathVariable Long id, @RequestBody BlogRequestDto blogRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        AuthenticatedUser authenticatedUser = jwtService.validateAndGetInfo(token);
        return blogService.updateContent(id, blogRequestDto, authenticatedUser.getUsername());
    }

    @DeleteMapping("/post/{id}")
    public StatusResponse deletePost (@PathVariable Long id, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        AuthenticatedUser authenticatedUser = jwtService.validateAndGetInfo(token);
        return blogService.deletePost(id, authenticatedUser.getUsername());
    }
}
