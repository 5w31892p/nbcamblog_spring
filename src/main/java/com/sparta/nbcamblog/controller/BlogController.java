package com.sparta.nbcamblog.controller;


import com.sparta.nbcamblog.dto.BlogRequestDto;
import com.sparta.nbcamblog.dto.BlogResponseDto;
import com.sparta.nbcamblog.dto.DeleteResponseDto;
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

    @PutMapping("/post/{id}")
    public BlogResponseDto updateContent (@PathVariable Long id, @RequestBody BlogRequestDto blogRequestDto, HttpServletRequest request) {
        return blogService.updateContent(id, blogRequestDto, request);
    }

    @DeleteMapping("/post/{id}")
    public DeleteResponseDto deletePost (@PathVariable Long id,  HttpServletRequest request) {
        return blogService.deletePost(id, request);
    }
}
