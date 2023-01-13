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

import com.sparta.nbcamblog.dto.BlogRequestDto;
import com.sparta.nbcamblog.dto.BlogResponseDto;
import com.sparta.nbcamblog.exception.StatusResponse;
import com.sparta.nbcamblog.service.BlogService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BlogController {
	private final BlogService blogService;

	@PostMapping("/post")
	public BlogResponseDto createContent(@RequestBody BlogRequestDto blogRequestDto, @AuthenticationPrincipal UserDetails userDetails) {
		return blogService.createContent(blogRequestDto, userDetails.getUsername());
	}

	@GetMapping("/posts")
	public List<BlogResponseDto> getContents() {
		return blogService.getContents();
	}

	@GetMapping("/post/{id}")
	public BlogResponseDto getContent(@PathVariable Long id) {
		return blogService.getContent(id);
	}

	@PutMapping("/post/{id}")
	public BlogResponseDto updateContent(@PathVariable Long id, @RequestBody BlogRequestDto blogRequestDto, @AuthenticationPrincipal UserDetails userDetails) {
		return blogService.updateContent(id, blogRequestDto, userDetails.getUsername());
	}

	@DeleteMapping("/post/{id}")
	public StatusResponse deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
		return blogService.deletePost(id, userDetails.getUsername());
	}

	@GetMapping("/postlike/{postId}")
	public StatusResponse addLike(@PathVariable Long postId, @AuthenticationPrincipal UserDetails userDetails) {
		return this.blogService.addLike(postId, userDetails.getUsername());
	}
}
