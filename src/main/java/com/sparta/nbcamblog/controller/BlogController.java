package com.sparta.nbcamblog.controller;


import com.sparta.nbcamblog.dto.BlogRequestDto;
import com.sparta.nbcamblog.dto.BlogResponseDto;
import com.sparta.nbcamblog.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;

    @PostMapping("/post")
    public BlogResponseDto createContent(@RequestBody BlogRequestDto blogRequestDto, HttpServletRequest request) {
        return blogService.createContent(blogRequestDto, request);
    }

    @GetMapping("/posts")
    public List<BlogResponseDto> getContents() {
        return blogService.getContents();
    }

    @GetMapping("/post/{id}")
    public BlogResponseDto getContent (@PathVariable Long id) {
        return blogService.getContent(id);
    }

    @GetMapping("/post/name")
    public List<BlogResponseDto> getByUsername(@RequestParam String username) {
        return blogService.getByUsername(username);
    }

    @PutMapping("/post/{id}")
    public String updateContent (@PathVariable Long id, @RequestBody BlogRequestDto blogRequestDto, HttpServletRequest request) {
        return blogService.updateContent(id, blogRequestDto, request);
    }

    @DeleteMapping("/post/{id}")
    public String deletePost (@PathVariable Long id, @RequestBody HttpServletRequest request) {
        return blogService.deletePost(id, request);
    }
}