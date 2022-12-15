package com.sparta.nbcamblog.controller;


import com.sparta.nbcamblog.dto.BlogRequestDto;
import com.sparta.nbcamblog.dto.BlogResponseDto;
import com.sparta.nbcamblog.dto.DeletePostDto;
import com.sparta.nbcamblog.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;

    @PostMapping("/post")
    public BlogResponseDto createContent(@RequestBody BlogRequestDto blogRequestDto) {
        return blogService.createContent(blogRequestDto);
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
    public BlogResponseDto updateContent (@PathVariable Long id, @RequestBody BlogRequestDto blogRequestDto) {
        return blogService.updateContent(id, blogRequestDto);
    }

    @DeleteMapping("/post/{id}")
    public DeletePostDto deletePost (@PathVariable Long id, @RequestBody BlogRequestDto blogRequestDto) {
        return blogService.deletePost(id, blogRequestDto);
    }
}
